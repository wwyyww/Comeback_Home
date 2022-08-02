package TheEarthGuard.ComeBackHome.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class CaseRequestDto {
//    @NotBlank(message = "실종자 이름은 필수 입력값입니다.")
    private String missing_name;

//    @NotBlank(message = "실종자 나이는 필수 입력값입니다.")
    private Integer missing_age;

//    @NotBlank(message = "실종자 성별은 필수 입력값입니다.")
    private Boolean missing_sex;

    private String missing_desc;

//    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missing_area;
//    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missing_lng;
//    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missing_lat;

//    @NotBlank(message = "실종 추정 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time_start;

//    @NotBlank(message = "실종 추정 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time_end;
}


