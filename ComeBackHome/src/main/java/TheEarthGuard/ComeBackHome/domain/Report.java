package TheEarthGuard.ComeBackHome.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@Builder
@Getter
@Entity
@Table(name="reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long report_id;

    @ManyToOne
    @JoinColumn(name = "cases_id")
    private Case cases;

    @ManyToOne
    @JoinColumn(name="users_id")
    private User user;

    private String witness_area;
    private Timestamp witness_time; // 수정 가능성 있음
    private String witness_txt;
    private String witness_pic;
    private Boolean is_alert;

    // 생성자
}
