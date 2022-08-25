package TheEarthGuard.ComeBackHome.domain;

import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    private String witnessRegion;

    private LocalDateTime witnessTime;

    private String witness_desc;

    @ColumnDefault("false")
    private Boolean is_alert;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Warn> warns;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FileEntity> witnessPics;

    @Builder
    @SuppressFBWarnings(justification = "Generated code")
    public Report(Long id, Case cases, User user, String witness_title, String witness_area, Double witness_lat, Double witness_lng, String witnessRegion, LocalDateTime witnessTime, String witness_desc, Boolean is_alert, List<Warn> warns, List<FileEntity> witnessPics) {
        this.id = id;
        this.cases = cases;
        this.user = user;
        this.witness_title = witness_title;
        this.witness_area = witness_area;
        this.witness_lat = witness_lat;
        this.witness_lng = witness_lng;
        this.witnessRegion = witnessRegion;
        this.witnessTime = witnessTime;
        this.witness_desc = witness_desc;
        this.is_alert = is_alert;
        this.warns = warns;
        this.witnessPics = witnessPics;
    }

    public void updateReport(ReportRequestDto dto){
        this.witness_title = dto.getWitness_title();
        this.witness_area = dto.getWitness_area();
        this.witnessRegion = dto.getWitnessRegion();
        this.witness_desc = dto.getWitness_desc();
        this.witness_lat =  Double.parseDouble(dto.getWitness_lat());
        this.witness_lng = Double.parseDouble(dto.getWitness_lng());
        this.witnessTime = dto.getWitnessTime();
        this.witnessRegion = this.witness_area.substring(0,3);
    }
}
