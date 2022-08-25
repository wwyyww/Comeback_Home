package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CaseSaveRequestDto {

    private User user;

    @NotBlank(message = "실종자 이름은 필수 입력값입니다.")
    private String missingName;

    @NotNull(message = "실종자 나이는 필수 입력값입니다.")
    private Integer missingAge;

    @NotNull(message = "실종자 성별은 필수 입력값입니다.")
    private Boolean missingSex;

    @NotBlank(message = "실종 설명은 필수 입력값입니다.")
    private String missingDesc;

    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missingArea;
    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missingLng;
    @NotBlank(message = "실종 지역은 필수 입력값입니다.")
    private String missingLat;
    @NotNull(message = "실종자 특성은 필수 입력값입니다.")
    private Integer missingFeature;

    @NotNull(message = "실종 추정 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missingTimeStart;

    @NotNull(message = "실종 추정 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missingTimeEnd;


    private List<MultipartFile> missingPics;

    public Case toEntity(){
        return Case.builder()
            .user(this.user)
            .missingName(this.missingName)
            .missingAge(this.missingAge)
            .missingSex(this.missingSex)
            .missingDesc(this.missingDesc)
            .missingFeature(this.missingFeature)
            .missingArea(this.missingArea)
            .missingRegion(this.missingArea.substring(0,2)) // 지역명 앞 2글자만
            .missingLat(Double.parseDouble(this.missingLat))
            .missingLng(Double.parseDouble(this.missingLng))
            .missingTimeStart(this.missingTimeStart)
            .missingTimeEnd(this.missingTimeEnd)
            .build();
    }

}

