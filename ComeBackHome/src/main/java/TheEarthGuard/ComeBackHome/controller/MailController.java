package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.MailDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.MailService;
import TheEarthGuard.ComeBackHome.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import groovy.util.logging.Slf4j;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MailController {
    private final MailService mailService;
    private final UserService userService;
    private final CaseService caseService;

    @SuppressFBWarnings(justification = "Generated code")
    public MailController(MailService mailService,CaseService caseService, UserService userService){
        this.mailService = mailService;
        this.caseService = caseService;
        this.userService = userService;
    }

    @GetMapping(value = "/ImageSave/{id}" )
    @ResponseBody
    public void ImgSaveTest(@RequestParam HashMap<Object, Object> param, final HttpServletRequest request, @PathVariable("id") Long id, @CurrentUser User user) throws Exception {
        User currentUser = userService.findByEmail(user.getEmail());
        Optional<Case> caseEntity = caseService.findCase(id);

        if (currentUser == null && caseEntity.isPresent()) {
            return ;
        }

        String binaryData = request.getParameter("imgSrc");
        FileOutputStream stream = null;

        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String fileDirPath = bundle.getString("tmpfilePath");  // parent 폴더

        try {
            if(binaryData == null || binaryData.trim().equals("")) {
                throw new Exception();
            }
            binaryData = binaryData.replaceAll("data:image/png;base64,", "");
            byte[] file = Base64.decodeBase64(binaryData);
            String fileName =  UUID.randomUUID().toString() + ".png";


            stream = new FileOutputStream(fileDirPath +  FilenameUtils.getName(fileName));
            stream.write(file);
            stream.close();
            System.out.println("캡처 저장");

            //파일 불러오기
            File savedfile = new File(fileDirPath, fileName);

            // 3. 메일 보내기
            if (caseEntity.isPresent()) {
                MailDto mailDto = MailDto.builder()
                    .from("ComeBackHome_Admin")
                    .address(currentUser.getEmail())
                    .title(
                        "[ComeBackHome] 실종 사건(실종자:" + caseEntity.get().getMissingName() + ") 정보 공유")
                    .content("실종자 ")
                    .build();

                mailService.sendMultipleMessage(mailDto, savedfile);
            }

            }catch (Exception e){
            e.printStackTrace();
            System.out.println("에러 발생");
        }finally {
            if(stream!=null){
                stream.close();
            }
        }

    }


}
