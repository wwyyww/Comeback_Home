package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.User;
import java.time.LocalDateTime;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class CaseListResponseDto {

    private Long userId;
    private String userEmail;
    private String userName;

    private Long caseId;
    private Boolean isFind;
    private Integer hitCnt;

    private String missingName;
    private Integer missingAge;
    private Boolean missingSex;
    private Integer missingFeature;
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
        this.hitCnt = caseEntity.getHitCnt();
        this.missingName = caseEntity.getMissingName();
        this.missingAge = caseEntity.getMissingAge();
        this.missingSex = caseEntity.getMissingSex();
        this.missingDesc = caseEntity.getMissingDesc();
        this.missingFeature = caseEntity.getMissingFeature();
        this.missingArea = caseEntity.getMissingArea();
        this.missingRegion = caseEntity.getMissingRegion();
        this.missingLat = caseEntity.getMissingLat();
        this.missingLng = caseEntity.getMissingLng();
        this.missingTimeStart = caseEntity.getMissingTimeStart();
        this.missingTimeEnd = caseEntity.getMissingTimeEnd();
        if(! caseEntity.getMissingPics().isEmpty()) {
            this.missingPicThumb = caseEntity.getMissingPics().get(0);
        }
        this.createdTime = caseEntity.getCreatedTime();
    }

    @Builder
    @SuppressFBWarnings(justification = "Generated code")
    public CaseListResponseDto(Long userId, String userEmail, String userName, Long caseId,
        Boolean isFind, Integer hitCnt, String missingName, Integer missingAge,
        Boolean missingSex, Integer missingFeature, String missingDesc, String missingArea,
        String missingRegion, Double missingLng, Double missingLat,
        LocalDateTime missingTimeStart, LocalDateTime missingTimeEnd,
        FileEntity missingPicThumb, LocalDateTime createdTime) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.caseId = caseId;
        this.isFind = isFind;
        this.hitCnt = hitCnt;
        this.missingName = missingName;
        this.missingAge = missingAge;
        this.missingSex = missingSex;
        this.missingFeature = missingFeature;
        this.missingDesc = missingDesc;
        this.missingArea = missingArea;
        this.missingRegion = missingRegion;
        this.missingLng = missingLng;
        this.missingLat = missingLat;
        this.missingTimeStart = missingTimeStart;
        this.missingTimeEnd = missingTimeEnd;
        this.missingPicThumb = missingPicThumb;
        this.createdTime = createdTime;
    }

    public Page<CaseListResponseDto> toDtoList(Page<Case> pagingList) {
        Page<CaseListResponseDto> caseListResponseDto = pagingList.map(m ->
                CaseListResponseDto.builder()
                        .userId(m.getUser().getId())
                        .userEmail(m.getUser().getEmail())
                        .userName(m.getUser().getName())
                        .caseId(m.getCaseId())
                        .isFind(m.getIsFind())
                        .hitCnt(m.getHitCnt())
                        .missingName(m.getMissingName())
                        .missingAge(m.getMissingAge())
                        .missingSex(m.getMissingSex())
                        .missingDesc(m.getMissingDesc())
                        .missingFeature(m.getMissingFeature())
                        .missingArea(m.getMissingArea())
                        .missingRegion(m.getMissingRegion())
                        .missingLat(m.getMissingLat())
                        .missingLng(m.getMissingLng())
                        .missingTimeStart(m.getMissingTimeStart())
                        .missingTimeEnd(m.getMissingTimeEnd())
                        .missingPicThumb(m.getMissingPics().get(0))
                        .createdTime(m.getCreatedTime())
                        .build());
            return caseListResponseDto;
    }
}
