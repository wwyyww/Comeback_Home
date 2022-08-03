package TheEarthGuard.ComeBackHome.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name="reports")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Report extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long report_id;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case cases;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String witness_title;
    private String witness_area;
    private Double witness_lat;
    private Double witness_lng;

    private Timestamp witness_time;

    private String witness_desc;
    private String witness_pic;

    @ColumnDefault("false")
    private Boolean is_alert;

    @Builder
    public Report(Long report_id, Case cases, User user,
                  String witness_title, String witness_area, Double witness_lat, Double witness_lng,
                  Timestamp witness_time, String witness_desc, String witness_pic, Boolean is_alert) {
        this.report_id = report_id;
        this.cases = cases;
        this.user = user;
        this.witness_title = witness_title;
        this.witness_area = witness_area;
        this.witness_lat = witness_lat;
        this.witness_lng = witness_lng;
        this.witness_time = witness_time;
        this.witness_desc = witness_desc;
        this.witness_pic = witness_pic;
        this.is_alert = is_alert;
    }
}
