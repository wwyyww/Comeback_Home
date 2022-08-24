package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.repository.FileRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Likelihood;
import com.google.cloud.vision.v1.SafeSearchAnnotation;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

//    @Value("${spring.servlet.multipart.location}")
//    private String fileDirPath; // parent 폴더


    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // 파일 저장
    public List<FileEntity> parseFileInfo(List<MultipartFile> files) throws Exception {
        List<FileEntity> fileList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(files)) {
            // 파일 저장 디렉토리 있는지 확인
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            String fileDirPath = bundle.getString("basefilePath");  // parent 폴더

            File uploadPath = new File(fileDirPath);

            if (!uploadPath.exists()) {
                if(!uploadPath.mkdirs())
                {
                    System.err.println("디렉토리 생성 실패");
                }
            }

            for (MultipartFile multipartFile : files) {
                // 파일 확장자 검사 (jpeg, png만 허용)
                String fileType;
                String contentType = multipartFile.getContentType();

                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if(contentType.contains("image/jpeg"))
                        fileType = "image";
                    else if(contentType.contains("image/png"))
                        fileType = "image";
                    else
                        break;
                }

                String orgFilename = multipartFile.getOriginalFilename();
                orgFilename = orgFilename.substring(orgFilename.lastIndexOf("\\") + 1);

                UUID uuid = UUID.randomUUID();
                String savedFilename = uuid.toString() + "_" + orgFilename;

                try {
                    InputStream initialStream = multipartFile.getInputStream();

                    // 파일 저장
                    File saveFile = new File(fileDirPath, FilenameUtils.getName(savedFilename)) ; // <<해결 필요>>
                    multipartFile.transferTo(saveFile);

                    // 유해 이미지 검사
//                    FileInputStream inputStream = new FileInputStream(saveFile);
//                    Boolean result = detectSafeSearch(inputStream);
//
//                    if (!result){ // 유해 이미지시 삭제
//                        Boolean deleteResult = saveFile.delete();
//                        System.out.println("deleteResult" + deleteResult);
//                        return null;
//                    }

                    // 썸네일 생성
                    if (checkImageType(saveFile)) {
                        FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, FilenameUtils.getName("s_" + savedFilename)));
                        Thumbnailator.createThumbnail(initialStream, thumbnail, 100, 100);

                        thumbnail.close();
                    }

                    // FileEntity의 생성
                    FileEntity fileEntity = FileEntity.builder()
                        .orgFileName(orgFilename)
                        .savedFileName(savedFilename)
                        .fileSize(multipartFile.getSize())
                        .fileType(fileType)
                        .build();

                    // 리스트에 추가
                    fileList.add(fileEntity);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            } // end for
        }
        return fileList;
    }

    // 파일 resource로 로드하기
    public Resource loadAsResource(String filename) throws MalformedURLException {
        return new UrlResource("file:" + createPath(filename));
    }


    public String createPath(String filename) {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String fileDirPath = bundle.getString("basefilePath");  // parent 폴더

        return fileDirPath + "\\" +  FilenameUtils.getName(filename);
    }

    private Boolean checkImageType(File file){
        try {
            String contentType = Files.probeContentType(file.toPath());
            if(contentType != null){
                return contentType.startsWith("image");
            }
            return false;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    // 유해 이미지 여부 검사
    public Boolean detectSafeSearch(FileInputStream fileStream) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        // 검사할 이미지 바이트 단위로
        ByteString imgBytes = ByteString.readFrom(fileStream);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
        AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());

                }

                SafeSearchAnnotation annotation = res.getSafeSearchAnnotation();
                Likelihood resultAdult = annotation.getAdult();
                Likelihood resultMedical = annotation.getMedical();
                Likelihood resultSpoof= annotation.getSpoof();
                Likelihood resultViolence = annotation.getViolence();
                Likelihood resultRacy = annotation.getRacy();


                System.out.format(
                    "adult: %s%nmedical: %s%nspoofed: %s%nviolence: %s%nracy: %s%n",
                    resultAdult,
                    resultMedical,
                    resultSpoof,
                    resultViolence,
                    resultRacy);

                if (resultAdult.equals(Likelihood.LIKELY) || resultAdult.equals(Likelihood.VERY_LIKELY) ||
                    resultMedical.equals(Likelihood.LIKELY) || resultMedical.equals(Likelihood.VERY_LIKELY) ||
                    resultSpoof.equals(Likelihood.LIKELY) || resultSpoof.equals(Likelihood.VERY_LIKELY) ||
                    resultViolence.equals(Likelihood.LIKELY) || resultViolence.equals(Likelihood.VERY_LIKELY) ||
                    resultRacy.equals(Likelihood.LIKELY) || resultRacy.equals(Likelihood.VERY_LIKELY)
                ){
                    return false;
                }
            }
        }
        return true;
    }


    // api key 연결
    public void authExplicit() throws IOException {
        ClassPathResource keyPath = new ClassPathResource("credential/ComeBackHome-90c138d3d128.json");
        GoogleCredentials.fromStream(keyPath.getInputStream()).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        System.out.println("Google Account Init Completed");
    }
}

