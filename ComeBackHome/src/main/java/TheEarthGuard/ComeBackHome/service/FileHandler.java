package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.FileEntity;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHandler {

//    @Value("${file.dir}/")
    private final String fileDirPath = "D:\\ComeBackHome";

//    public FileHandler(){};

    // 파일 저장
    public List<FileEntity> parseFileInfo(List<MultipartFile> files) throws Exception {
        List<FileEntity> fileList = new ArrayList<>();
//        String uploadFolder = "D:\\ComeBackHome"; //parent 폴더

        if (!CollectionUtils.isEmpty(files)) {
            // 파일 저장 디렉토리 있는지 확인
            String uploadFolderPath = getFolder(); // child 폴더
            File uploadPath = new File(fileDirPath, uploadFolderPath);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            for (MultipartFile multipartFile : files) {
                // 파일 확장자 검사 (jpeg, png만 허용)
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
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
                        .fileType(originalFileExtension)
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

    public String createPath(String filepath, String filename) {
        return fileDirPath + "\\" + filepath + "\\" + filename;
    }

    public String createThumbPath(String filepath, String filename) {
        return fileDirPath + "\\" + filepath + "\\_s" + filename;
//        return URLEncoder.encode(folderPath + "/s_" +uuid + "_" +fileName,"UTF-8");
    }

    private String getFolder() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String str = sdf.format(date);
//        return str.replace("-", File.separator);
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

