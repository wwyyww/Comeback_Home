package TheEarthGuard.ComeBackHome.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportFormDto {

    private String witness_title;
    private String witness_area;
    private String witness_lat;
    private String witness_lng;
    private String witness_desc;
    private String witness_pic;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime witness_time;

}
