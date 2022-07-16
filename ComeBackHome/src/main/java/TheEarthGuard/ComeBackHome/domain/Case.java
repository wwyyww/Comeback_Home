package TheEarthGuard.ComeBackHome.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cases")
public class Case {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long case_id;
    private String finder_id;
    private Boolean is_find; //INTS
    private Integer report_cnt; //
    private Integer hit_cnt;
    private String missing_pic;
    private String missing_name;
    private Integer missing_age; //
    private Boolean missing_sex; //
    private String missing_desc;
    private String missing_area;
    private Double missing_lat;
    private Double missing_lng;
    private Timestamp missing_time; //

    // 생성자
    public Case(String finder_id, Boolean is_find, Integer report_cnt, Integer hit_cnt,
        String missing_pic, String missing_name, Integer missing_age, Boolean missing_sex,
        String missing_desc, String missing_area, Double missing_lat, Double missing_lng,
        Timestamp missing_time) {
        this.finder_id = finder_id;
        this.is_find = is_find;
        this.report_cnt = report_cnt;
        this.hit_cnt = hit_cnt;
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

    public Case() {

    }

    // Getter & Setter

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    public String getFinder_id() {
        return finder_id;
    }

    public void setFinder_id(String finder_id) {
        this.finder_id = finder_id;
    }

    public Boolean getIs_find() {
        return is_find;
    }

    public void setIs_find(Boolean is_find) {
        this.is_find = is_find;
    }

    public Integer getReport_cnt() {
        return report_cnt;
    }

    public void setReport_cnt(Integer report_cnt) {
        this.report_cnt = report_cnt;
    }

    public Integer getHit_cnt() {
        return hit_cnt;
    }

    public void setHit_cnt(Integer hit_cnt) {
        this.hit_cnt = hit_cnt;
    }

    public String getMissing_pic() {
        return missing_pic;
    }

    public void setMissing_pic(String missing_pic) {
        this.missing_pic = missing_pic;
    }

    public String getMissing_name() {
        return missing_name;
    }

    public void setMissing_name(String missing_name) {
        this.missing_name = missing_name;
    }

    public Integer getMissing_age() {
        return missing_age;
    }

    public void setMissing_age(Integer missing_age) {
        this.missing_age = missing_age;
    }

    public Boolean getMissing_sex() {
        return missing_sex;
    }

    public void setMissing_sex(Boolean missing_sex) {
        this.missing_sex = missing_sex;
    }

    public String getMissing_desc() {
        return missing_desc;
    }

    public void setMissing_desc(String missing_desc) {
        this.missing_desc = missing_desc;
    }

    public String getMissing_area() {
        return missing_area;
    }

    public void setMissing_area(String missing_area) {
        this.missing_area = missing_area;
    }

    public Double getMissing_lat() {
        return missing_lat;
    }

    public void setMissing_lat(Double missing_lat) {
        this.missing_lat = missing_lat;
    }

    public Double getMissing_lng() {
        return missing_lng;
    }

    public void setMissing_lng(Double missing_lng) {
        this.missing_lng = missing_lng;
    }

    public Timestamp getMissing_time() {
        return missing_time;
    }

    public void setMissing_time(Timestamp missing_time) {
        this.missing_time = missing_time;
    }
}
