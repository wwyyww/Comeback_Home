package TheEarthGuard.ComeBackHome.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Setter
@Table(name="reports")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Report extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "caseId")
    private Case cases;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String witness_title;
    private String witness_area;
    private Double witness_lat;
    private Double witness_lng;

    private LocalDateTime witness_time;

    private String witness_desc;

    @ColumnDefault("false")
    private Boolean is_alert;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Warn> warns;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FileEntity> witnessPics;

    @Builder
    public Report(Long id, Case cases, User user,
                  String witness_title, String witness_area, Double witness_lat, Double witness_lng,
                  LocalDateTime witness_time, String witness_desc, Boolean is_alert) {
        this.id = id;
        this.cases = cases;
        this.user = user;
        this.witness_title = witness_title;
        this.witness_area = witness_area;
        this.witness_lat = witness_lat;
        this.witness_lng = witness_lng;
        this.witness_time = witness_time;
        this.witness_desc = witness_desc;
        this.is_alert = is_alert;
    }
}
