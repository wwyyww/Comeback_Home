package TheEarthGuard.ComeBackHome.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    @JoinColumn(name="finder_id")
    private User user;

    private Boolean is_find;
    private Integer report_cnt;
    private Integer hit_cnt;
    private String missing_pic;
    private String missing_name;
    private Integer missing_age;
    private Boolean missing_sex;
    private String missing_desc;
    private String missing_area;
    private Double missing_lat;
    private Double missing_lng;
    private Timestamp missing_time;

    @Builder
    public Case(Long case_id, User user, String missing_pic, String missing_name, Integer missing_age,
        Boolean missing_sex, String missing_desc, String missing_area, Double missing_lat,
        Double missing_lng, Timestamp missing_time) {
        this.case_id = case_id;
        this.user = user;
        this.is_find = false;
        this.report_cnt = 0;
        this.hit_cnt = 0;
        this.missing_pic = missing_pic;
        this.missing_name = missing_name;
        this.missing_age = missing_age;
        this.missing_sex = missing_sex;
        this.missing_desc = missing_desc;
        this.missing_area = missing_area;
        this.missing_lat = missing_lat;
        this.missing_lng = missing_lng;
        this.missing_time = missing_time;
    }
}
