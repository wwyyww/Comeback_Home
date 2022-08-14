package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.BaseTimeEntity;
import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReportRequestDto extends BaseTimeEntity {
    
    private Case cases;
    private User user;

    @NotBlank(message = "제보 제목은 필수 입력값입니다.")
    private String witness_title;

    @NotBlank(message = "목격 지역은 필수 입력값입니다.")
    private String witness_area;
    private String witness_lat;
    private String witness_lng;

    @NotBlank(message = "제보 내용은 필수 입력값입니다.")
    private String witness_desc;

    @NotNull(message = "목격 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime witness_time;

    private List<MultipartFile> witnessPics;


    public Report toEntity(){
        Report report=Report.builder()
                .witness_title(witness_title)
                .witness_area(witness_area)
                .witness_desc(witness_desc)
                .witness_lat(Double.valueOf(witness_lat))
                .witness_lng(Double .valueOf(witness_lng))
                .witness_time(Timestamp.valueOf(witness_time))
                .is_alert(Boolean.FALSE)
                .user(user)
                .cases(cases)
                .build();

        return report;
    }
}
