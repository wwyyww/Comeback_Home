package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.repository.FileRepository;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

//    @Value("${file.dir}/")
    private final String fileDirPath = "D:\\ComeBackHome"; // parent 폴더

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // 파일 저장
    public List<FileEntity> parseFileInfo(List<MultipartFile> files) throws Exception {
        List<FileEntity> fileList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(files)) {
            // 파일 저장 디렉토리 있는지 확인
            String uploadFolderPath = getFolder(); // child 폴더
            File uploadPath = new File(fileDirPath, uploadFolderPath);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
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
                    File saveFile = new File(uploadPath.getAbsolutePath(), savedFilename);
                    multipartFile.transferTo(saveFile);


                    // 썸네일 생성
                    if (checkImageType(saveFile)) {
                        FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + savedFilename));
                        Thumbnailator.createThumbnail(initialStream, thumbnail, 100, 100);

                        thumbnail.close();
                    }

                    // FileEntity의 생성
                    FileEntity fileEntity = FileEntity.builder()
                        .orgFileName(orgFilename)
                        .savedFileName(savedFilename)
                        .fileSize(multipartFile.getSize())
                        .fileType(fileType)
                        .uploadPath(uploadFolderPath)
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
    public Resource loadAsResource(String filepath,String filename) throws MalformedURLException {
        return new UrlResource("file:" + createPath(filepath, filename));
    }


    public String createPath(String filepath, String filename) {
        return fileDirPath + "\\" + filepath + "\\" + filename;
    }
    public String createPath1(String filepath) {
        return fileDirPath + "\\" + filepath;
    }


    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }

    private Boolean checkImageType(File file){
        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}

