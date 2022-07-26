package TheEarthGuard.ComeBackHome.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="reports")
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long report_id;
    private String case_id;
    private String user_id;
    private String witness_area;
    private Timestamp witness_time; // 수정 가능성 있음
    private String witness_txt;
    private String witness_pic;
    private Boolean is_alert;

    // 생성자
    public Report(String case_id, String user_id, String witness_area, Timestamp witness_time,
        String witness_txt, String witness_pic, Boolean is_alert) {
        this.case_id = case_id;
        this.user_id = user_id;
        this.witness_area = witness_area;
        this.witness_time = witness_time;
        this.witness_txt = witness_txt;
        this.witness_pic = witness_pic;
        this.is_alert = is_alert;
    }

    public Report() {

    }

    // Getter / Setter
    public Long getReport_id() {
        return report_id;
    }

    public void setReport_id(Long reportId) {
        this.report_id = reportId;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String caseId) {
        this.case_id = caseId;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String userId) {
        this.user_id = userId;
    }

    public String getWitness_area() {
        return witness_area;
    }

    public void setWitness_area(String witnessArea) {
        this.witness_area = witnessArea;
    }

    public Timestamp getWitness_time() {
        return witness_time;
    }

    public void setWitness_time(Timestamp witnessTime) {
        this.witness_time = witnessTime;
    }

    public String getWitness_txt() {
        return witness_txt;
    }

    public void setWitness_txt(String witnessTxt) {
        this.witness_txt = witnessTxt;
    }

    public String getWitness_pic() {
        return witness_pic;
    }

    public void setWitness_pic(String witnessPic) {
        this.witness_pic = witnessPic;
    }

    public Boolean getIs_alert() {
        return is_alert;
    }

    public void setIs_alert(Boolean isAlert) {
        this.is_alert = isAlert;
    }
}
