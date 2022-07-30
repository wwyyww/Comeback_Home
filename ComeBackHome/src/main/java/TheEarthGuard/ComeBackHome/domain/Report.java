package TheEarthGuard.ComeBackHome.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;



@Builder
@Getter
@Setter
@Entity
@Table(name="reports")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Report {
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
}
