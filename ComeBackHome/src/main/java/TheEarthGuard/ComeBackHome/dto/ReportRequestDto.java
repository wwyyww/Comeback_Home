package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequestDto {

    private Long id;
    private Case cases;
    private User user;
    private String witness_title;
    private String witness_area;
    private String witness_lat;
    private String witness_lng;
    private String witness_desc;
    private String witness_pic;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime witness_time;

    public Report toEntity(){
        Report report=Report.builder()
                .id(id)
                .witness_title(witness_title)
                .witness_area(witness_area)
                .witness_desc(witness_desc)
                .witness_lat(Double.valueOf(witness_lat))
                .witness_lng(Double .valueOf(witness_lng))
                .witness_pic(witness_pic)
                .witness_time(Timestamp.valueOf(witness_time))
                .is_alert(Boolean.FALSE)
                .user(user)
                .cases(cases)
                .build();

        return report;
    }
}
