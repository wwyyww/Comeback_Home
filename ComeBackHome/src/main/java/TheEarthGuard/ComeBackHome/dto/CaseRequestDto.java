package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class CaseRequestDto {

    @NotBlank(message = "실종자 이름은 필수 입력값입니다.")

    private String missing_name;

    @NotNull(message = "실종자 나이는 필수 입력값입니다.")
    private Integer missing_age;


    @NotNull(message = "실종자 성별은 필수 입력값입니다.")
    private Boolean missing_sex;

    private String missing_desc;

    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missing_area;
    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missing_lng;
    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missing_lat;

    @NotNull(message = "실종 추정 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time_start;

    @NotNull(message = "실종 추정 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time_end;


    public Case toCase(User user){
        return Case.builder()
            .user(user)
            .missing_pic_path("file_path")
            .missing_pic_name("file_name")
            .missing_name(this.missing_name)
            .missing_age(this.missing_age)
            .missing_sex(this.missing_sex)
            .missing_desc(this.missing_desc)
            .missing_area(this.missing_area)
            .missing_region(this.missing_area.substring(0,2)) // 지역명 앞 2글자만
            .missing_lat(Double.parseDouble(this.missing_lat))
            .missing_lng(Double.parseDouble(this.missing_lng))
            .missing_time_start(this.missing_time_start)
            .missing_time_end(this.missing_time_end)
            .build();
    }
}

