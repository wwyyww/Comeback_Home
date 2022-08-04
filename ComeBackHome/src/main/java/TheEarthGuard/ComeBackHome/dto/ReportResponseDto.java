package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class ReportResponseDto {
    private Long id;

    private String missing_name; //실종자의 이름

    private String name; //작성자 이름
    private String witness_title;
    private String witness_area;
    private Double witness_lat;
    private Double witness_lng;
    private String witness_desc;
    private String witness_pic;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime witness_time;

    //Entity -> DTO
    public ReportResponseDto(Report report){
        this.id = report.getId();
        this.witness_title = report.getWitness_title();
        this.witness_area = report.getWitness_area();
        this.witness_desc = report.getWitness_desc();
        this.witness_lat = report.getWitness_lat();
        this.witness_lng = report.getWitness_lng();
        this.name = report.getUser().getName();
        this.missing_name = report.getCases().getMissing_name();

    }

}
