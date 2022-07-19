package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.respoitory.CaseRepository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Transactional
public class CaseService {
    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    /**
     * 사건 등록하기
     */
    public Long UploadCase(Case caseObj) {
        System.out.println("[CASE_SERVICE] caseObj.getMissing_time" + caseObj.getMissing_time());
//        System.out.println("[TEST] UploadCase Finder_id " + caseObj.getFinder_id());
//        validateDuplicateCase(caseObj); //중복 사건 검증
        caseRepository.save(caseObj);
        return caseObj.getCase_id();
    }

    // 수정 필요함.
    private void validateDuplicateCase(Case caseObj) {
        caseRepository.findByCaseId(caseObj.getCase_id())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 사건입니다.");
            });
    }

    /**
     * 전체 사건 조회
     */
    public List<Case> findCases() {
        return caseRepository.findAll();
    }

    /**
     * 사건 하나 조회
     */
    public Optional<Case> findOne(Long case_id) {
        return caseRepository.findByCaseId(case_id);
    }

    /**
     * 사건 검색
     */
//    public List<Case> search(String keyword){
//        List<Case> caseList = caseRepository.(keyword);
//        return caseList;
//
//    }
}
