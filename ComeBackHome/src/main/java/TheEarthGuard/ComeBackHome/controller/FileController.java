package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.service.FileService;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    // 사진 출력 (URL 접근)
    @ResponseBody
    @GetMapping("/images/{filepath}/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filepath, @PathVariable String filename){
        File file = new File(fileService.createPath(filepath, filename));
        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    // 사진 다운로드
    @GetMapping("/download/{filepath}/{filename}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable String filepath, @PathVariable String filename)
        throws MalformedURLException {
        Resource resource = fileService.loadAsResource(filepath, filename);
        String resourceName = resource.getFilename();

        String resourceOrgName = resourceName.substring(resourceName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();
        try{
            headers.add("Content-Disposition", "attachment; filename=" +
                new String(resourceOrgName.getBytes(StandardCharsets.UTF_8),"ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    // 사진 삭제
    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String fileType)
        throws UnsupportedEncodingException {
        File file;

        URLDecoder.decode(fileName,"UTF-8");
        System.out.println("DeleteFile "+ fileName + fileType );
        log.info("DeleteFile : ", URLDecoder.decode(fileName,"UTF-8"));

        file = new File(fileService.createPath1(fileName));
        System.out.println("file : "+ file.getAbsolutePath());
        boolean result = file.delete();
        System.out.println("result :  "+ result);

        // 원본 삭제
        if(fileType.equals("image")){
            System.out.println("원본도 삭제됨");
            String largeFileName = file.getAbsolutePath().replace("s_", "");
            file = new File(largeFileName);
            file.delete();
        }

        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    // 유해 이미지 필터링
//    @GetMapping("/test")
//    public String detectSafeSearch() throws IOException {
//        fileService.authExplicit();
//        String filePath = "D:\\ComeBackHome\\220821\\MYH20200731003200038_P4.jpg";
////        fileService.detectSafeSearch(filePath);
//
//        return "redirect:/";
//    }

}
