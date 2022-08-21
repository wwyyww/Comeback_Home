package TheEarthGuard.ComeBackHome.domain;

import TheEarthGuard.ComeBackHome.dto.CaseSaveRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer missingFeature;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String missingDesc;
    private String missingArea;
    private String missingRegion;
    private Double missingLat;
    private Double missingLng;
    private LocalDateTime missingTimeStart;
    private LocalDateTime missingTimeEnd;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Warn> warns;

    @JsonIgnore
    @OneToMany(mappedBy = "cases", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdTime desc")
    private List<Report> reports;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FileEntity> missingPics;

    @Builder
    public Case(Long caseId, User user, Boolean isFind, Integer hitCnt, Integer missingFeature,
        String missingName, Integer missingAge, Boolean missingSex, String missingDesc,
        String missingArea, String missingRegion, Double missingLat, Double missingLng,
        LocalDateTime missingTimeStart, LocalDateTime missingTimeEnd)
    {
        this.caseId = caseId;
        this.user = user;
        this.isFind = false;
        this.hitCnt = 0;
        this.missingFeature = missingFeature;
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

    public void updateIsFind(Boolean isFind) {
        this.isFind = isFind;
    }


    public void updateCase(CaseSaveRequestDto dto){
        this.missingName = dto.getMissingName();
        this.missingAge = dto.getMissingAge();
        this.missingSex = dto.getMissingSex();
        this.missingDesc = dto.getMissingDesc();
        this.missingFeature = dto.getMissingFeature();
        this.missingArea = dto.getMissingArea();
        this.missingLng = Double.parseDouble(String.valueOf(this.missingLng));
        this.missingLat = Double.parseDouble(String.valueOf(this.missingLat));
        this.missingTimeStart = dto.getMissingTimeStart();
        this.missingTimeEnd = dto.getMissingTimeEnd();
    }
}
