package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.dto.MailDto;
import groovy.util.logging.Slf4j;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;

    public void sendMultipleMessage(MailDto mailDto, File file) throws MessagingException, IOException {
        FileInputStream stream = null;

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(mailDto.getTitle());
            helper.setText(mailDto.getContent(), false);
            helper.setFrom(mailDto.getFrom());

            ResourceBundle bundle = ResourceBundle.getBundle("application");
            String fileDirPath = bundle.getString("tmpfilePath");  // parent 폴더

            String fileName = StringUtils.cleanPath(file.getName());
            File filePath = new File(fileDirPath, FilenameUtils.getName(fileName));

            stream = new FileInputStream(filePath);

            helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"),
                new ByteArrayResource(
                    IOUtils.toByteArray(stream)));

            helper.setTo(mailDto.getAddress());
            emailSender.send(message);

            stream.close();
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
