package TheEarthGuard.ComeBackHome.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cases")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Case {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long case_id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="finder_id")
    private User user;
    private Boolean is_find;
    private Integer report_cnt;
    private Integer hit_cnt;
    private String missing_pic_name;
    private String missing_pic_path;
    private String missing_name;
    private Integer missing_age;
    private Boolean missing_sex;
    private String missing_desc;
    private String missing_area;
    private String missing_region;
    private Double missing_lat;
    private Double missing_lng;
    private LocalDateTime missing_time_start;
    private LocalDateTime missing_time_end;

    @Builder
    public Case(Long case_id, User user, Boolean is_find, Integer report_cnt, Integer hit_cnt,
        String missing_pic_name, String missing_pic_path, String missing_name,
        Integer missing_age, Boolean missing_sex, String missing_desc, String missing_area,
        String missing_region, Double missing_lat, Double missing_lng,
        LocalDateTime missing_time_start, LocalDateTime missing_time_end) {
        this.case_id = case_id;
        this.user = user;
        this.is_find = false;
        this.report_cnt = 0;
        this.hit_cnt = 0;
        this.missing_pic_name = missing_pic_name;
        this.missing_pic_path = missing_pic_path;
        this.missing_name = missing_name;
        this.missing_age = missing_age;
        this.missing_sex = missing_sex;
        this.missing_desc = missing_desc;
        this.missing_area = missing_area;
        this.missing_region = missing_region;
        this.missing_lat = missing_lat;
        this.missing_lng = missing_lng;
        this.missing_time_start = missing_time_start;
        this.missing_time_end = missing_time_end;
    }
}
