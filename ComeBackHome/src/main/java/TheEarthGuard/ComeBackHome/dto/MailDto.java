package TheEarthGuard.ComeBackHome.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {
    private String from;
    private String address;
    private String title;
    private String content;
//    private MultipartFile attachFile;

    @Builder
    public MailDto(String from, String address, String title,
        String content) {
        this.from = from;
        this.address = address;
        this.title = title;
        this.content = content;
    }
}
