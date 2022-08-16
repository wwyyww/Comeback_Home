package TheEarthGuard.ComeBackHome.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
public class Case extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long caseId;

    @ManyToOne
    @NotNull
    @JoinColumn(name="finder_id") // finder_id
    private User user;
    private Boolean isFind;
//    private Integer warningCnt;
    private Integer hitCnt;

    private String missingName;
    private Integer missingAge;
    private Boolean missingSex;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String missingDesc;
    private String missingArea;
    private String missingRegion;
    private Double missingLat;
    private Double missingLng;
    private LocalDateTime missingTimeStart;
    private LocalDateTime missingTimeEnd;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Warn> warns;

    @OneToMany(mappedBy = "cases", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdTime desc")
    private List<Report> reports;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FileEntity> missingPics;

    @Builder
    public Case(Long caseId, User user, Boolean isFind, Integer hitCnt,
        String missingName, Integer missingAge, Boolean missingSex, String missingDesc,
        String missingArea, String missingRegion, Double missingLat, Double missingLng,
        LocalDateTime missingTimeStart, LocalDateTime missingTimeEnd)
    {
        this.caseId = caseId;
        this.user = user;
        this.isFind = false;
        this.hitCnt = 0;
        this.missingName = missingName;
        this.missingAge = missingAge;
        this.missingSex = missingSex;
        this.missingDesc = missingDesc;
        this.missingArea = missingArea;
        this.missingRegion = missingRegion;
        this.missingLat = missingLat;
        this.missingLng = missingLng;
        this.missingTimeStart = missingTimeStart;
        this.missingTimeEnd = missingTimeEnd;
    }

}
