package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
import TheEarthGuard.ComeBackHome.dto.CaseSaveRequestDto;
import TheEarthGuard.ComeBackHome.dto.WarnDto;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import TheEarthGuard.ComeBackHome.repository.WarnRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CaseService {
    private final CaseRepository caseRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final WarnRepository warnRepository;
    private Map<String, String> warnReasonList=new HashMap<>();


    public CaseService(CaseRepository caseRepository, UserRepository userRepository, WarnRepository warnRepository,
        FileService fileService) {
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
        this.warnRepository = warnRepository;
        this.fileService = fileService;
        warnReasonList.put("1", "허위신고");
        warnReasonList.put("2", "욕설/비하");
        warnReasonList.put("3", "낚시/놀람/도배");
        warnReasonList.put("4", "유출/사칭/사기");
        warnReasonList.put("5", "상업적 광고 및 판매");
        warnReasonList.put("6", "희롱 또는 괴롭힘");
        warnReasonList.put("7", "음란물/불건전한 만남 및 대화");
        warnReasonList.put("8", "정당/정치인 비하 및 선거운동");
    }

    /**
     * 사건 등록하기
     */
    @Transactional
    public Long uploadCase(CaseSaveRequestDto caseDto,  List<MultipartFile> files) throws Exception{
        Case newCase = caseDto.toEntity();

        List<FileEntity> missing_pictures = fileService.parseFileInfo(files);
        System.out.println("[CaseService-missing_pictures] 파일!" + missing_pictures);
        if(!missing_pictures.isEmpty()) {
            System.out.println("[CaseService-UploadCase] 파일 있음!" + missing_pictures);
            newCase.setMissingPics(missing_pictures);
        }

        caseRepository.save(newCase);
        return newCase.getCaseId();
    }

    //유효성 검사
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    /**
     * 사건  수정하기
     */
    @Transactional
    public Long updateCase(Long user_id,  Long case_id, CaseSaveRequestDto caseDto, List<MultipartFile> files) throws Exception{
        Optional<User> user = userRepository.findById(user_id);
        Optional<Case> caseEntity = Optional
            .ofNullable(caseRepository.findById(case_id).orElseThrow(() ->
                new IllegalArgumentException("사건 수정 실패 : 존재하지 않는 사건입니다")));

        if (caseEntity.get().getUser().getId() != user.get().getId()) {
            throw new IllegalAccessException("제보 업데이트 실패 : 올바른 사용자가 아닙니다.");
        }

        caseDto.setUser(user.get());
        Case updateCase = caseDto.toEntity();
        updateCase.setCreatedTime(caseEntity.get().getCreatedTime());
        caseRepository.save(updateCase);

        return case_id;
    }


    /**
     * 사건 삭제
     */
    @Transactional
    public void deleteCase(Long id, User user) {
        Optional<Case> caseEntity = caseRepository.findByCaseId(id);
        if (user.getId() == caseEntity.get().getUser().getId()) {
            caseRepository.deleteById(id);
        }else{
            new IllegalArgumentException("사건 삭제 실패 : 사용자가 일치하지 않음");
        }
    }

    /**
     * 사건 조회수
     */
    @Transactional
    public void countHitCase(Long caseId) {
        Optional<Case> caseEntity = caseRepository.findById(caseId);
        caseRepository.updateHitCase(caseId);
    }


    /**
     * 사건 신고
     */
    @Transactional
    public void warnCase(Long caseId, User user, WarnDto form) {
        Optional<Case> caseEntity = caseRepository.findById(caseId);
        User caseUser = caseEntity.get().getUser();

        // 사건 등록자 신고
        caseUser.updateWarnCount(caseUser.getWarning_cnt()+1);
        userRepository.saveAndFlush(caseUser);

        // 신고 객체 생성
        Warn warn = Warn.builder()
            .warnSender(caseUser)
                .warnReason(warnReasonList.get(form.getWarnReason()))
            .build();
        warnRepository.save(warn);

        // 사건에 신고 객체 추가
        caseEntity.get().getWarns().add(warn);

    }

    /**
     * 전체 사건 조회
     */
    public List<Case> getCaseList() {
        return caseRepository.findAll();
    }

    /**
     *  특정 사용자가 등록한 사건들 조회 (사용자 id 기반)
     */
    public Optional<List<Case>> findCaseByUser(User user) {
        return caseRepository.findAllByUser(user);
    }

    /**
     * 사건 하나 조회 (사건 id 기반)
     */
    public Optional<Case> findCase(Long case_id) {
        return caseRepository.findByCaseId(case_id);
    }



    /**
     * 사건 검색
     */
    public Optional<List<Case>> findbyMissingName(String keyword, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        return caseRepository.searchByMissingName(keyword, sex, age, area);
    }

    public Optional<List<Case>> findbyMissingArea(String keyword, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        return caseRepository.searchByMissingArea(keyword, sex, age, area);
    }

    public Optional<List<Case>> findbyFilters(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        return caseRepository.searchByFilters(sex, age, area);
    }

    public Optional<List<Case>> sortCasebyTime(){
        return caseRepository.casebyTime();
    }

}
