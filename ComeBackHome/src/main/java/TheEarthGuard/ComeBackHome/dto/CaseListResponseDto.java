package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class CaseListResponseDto {

    private Long userId;
    private String userEmail;
    private String userName;

    private Long caseId;
    private Boolean isFind;
    private Integer reportCnt;
    private Integer hitCnt;

    private String missingName;
    private Integer missingAge;
    private Boolean missingSex;
    private String missingDesc;
    private String missingArea;
    private String missingRegion;
    private Double missingLng;
    private Double missingLat;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missingTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime missingTimeEnd;

    private FileEntity missingPicThumb;

    private LocalDateTime createdTime;

    public CaseListResponseDto(Case caseEntity, User user)
    {
        this.userId = user.getId();
        this.userEmail = user.getEmail();
        this.userName = user.getUsername();
        this.caseId = caseEntity.getCaseId();
        this.isFind = caseEntity.getIsFind();
        this.reportCnt = caseEntity.getReportCnt();
        this.hitCnt = caseEntity.getHitCnt();
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
        this.missingPicThumb = caseEntity.getMissingPics().get(0);
        this.createdTime = caseEntity.getCreatedTime();
    }
}
