package TheEarthGuard.ComeBackHome.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class CaseFormDto {
    private String missing_name;
    private String missing_pic;
    private Integer missing_age;
    private Boolean missing_sex;
    private String missing_desc;
    private String missing_area;
    private String missing_lng;
    private String missing_lat;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time;
}
