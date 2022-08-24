package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReportResponseDto {

    private Long id;
    private Case cases;
    private User user;

    @NotBlank(message = "제보 제목은 필수 입력값입니다.")
    private String witness_title;

    @NotBlank(message = "목격 지역은 필수 입력값입니다.")
    private String witness_area;

    private String witnessRegion;

    private Double witness_lat;
    private Double witness_lng;

    @NotBlank(message = "제보 내용은 필수 입력값입니다.")
    private String witness_desc;

    @NotNull(message = "목격 날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime witnessTime;

    private LocalDateTime createdTime;

    private List<FileEntity> witnessPics;

    @SuppressFBWarnings(justification = "Generated code")
    public ReportResponseDto(Report report, User user) {
        this.cases = report.getCases();
        this.user = user;
        this.id = report.getId();
        this.witness_title = report.getWitness_title();
        this.witness_area = report.getWitness_area();
        this.witnessRegion = report.getWitnessRegion();
        this.witness_lat = report.getWitness_lat();
        this.witness_lng = report.getWitness_lng();
        this.witness_desc = report.getWitness_desc();
        this.witnessTime = report.getWitnessTime();
        this.witnessPics = report.getWitnessPics();
        this.createdTime = report.getCreatedTime();

    }
}
