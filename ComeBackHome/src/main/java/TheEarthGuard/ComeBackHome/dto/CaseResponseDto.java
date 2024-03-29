package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class CaseResponseDto {

    private Long userId;
    private String userEmail;
    private String userName;

    private Long caseId;
    private Boolean isFind;
    private Integer hitCnt;

    private String missingName;
    private Integer missingAge;
    private Boolean missingSex;
    private String missingDesc;
    private Integer missingFeature;
    private String missingArea;
    private String missingRegion;
    private Double missingLng;
    private Double missingLat;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missingTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missingTimeEnd;

    private List<FileEntity> missingPics;

    public CaseResponseDto(Case caseEntity, User user)
    {
        this.userId = user.getId();
        this.userEmail = user.getEmail();
        this.userName = user.getUsername();
        this.caseId = caseEntity.getCaseId();
        this.isFind = caseEntity.getIsFind();
        this.hitCnt = caseEntity.getHitCnt();
        this.missingFeature = caseEntity.getMissingFeature();
        this.missingName = caseEntity.getMissingName();
        this.missingAge = caseEntity.getMissingAge();
        this.missingSex = caseEntity.getMissingSex();
        this.missingDesc = caseEntity.getMissingDesc();
        this.missingArea = caseEntity.getMissingArea();
        this.missingRegion = caseEntity.getMissingRegion();
        this.missingLat = caseEntity.getMissingLat();
        this.missingLng = caseEntity.getMissingLng();
        this.missingTimeStart = caseEntity.getMissingTimeStart();
        this.missingTimeEnd = caseEntity.getMissingTimeEnd();
        this.missingPics = caseEntity.getMissingPics();
    }
}
