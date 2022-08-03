package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CaseResponseDto {
    private String name; //실찾자 작성자
    private Long case_id;
    private Boolean is_find;
    private Integer report_cnt;
    private Integer hit_cnt;
    private String missing_name;
    private String missing_pic;
    private Integer missing_age;
    private Boolean missing_sex;
    private String missing_desc;
    private String missing_area;
    private Double missing_lng;
    private Double missing_lat;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time_start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missing_time_end;


    private List<ReportResponseDto> reports;

    public CaseResponseDto(Case cases) {
        this.case_id = cases.getCase_id();
        this.is_find = cases.getIs_find();
        this.hit_cnt = cases.getHit_cnt();
        this.report_cnt = cases.getReport_cnt();
        this.missing_name = cases.getMissing_name();
//        this.missing_pic = cases.getMissing_pic();
        this.missing_age = cases.getMissing_age();
        this.missing_sex = cases.getMissing_sex();
        this.missing_desc = cases.getMissing_desc();
        this.missing_area = cases.getMissing_area();
        this.missing_lat = cases.getMissing_lat();
        this.missing_lng = cases.getMissing_lng();
        this.missing_time_start= cases.getMissing_time_start();
        this.missing_time_end = cases.getMissing_time_end();
        this.name = cases.getUser().getName();
        this.reports = cases.getReports().stream().map(ReportResponseDto::new).collect(Collectors.toList());
    }
}
