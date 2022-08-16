package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.dto.CaseResponseDto;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseRequestDto;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Transactional
public class CaseService {
    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    /**
     * 사건 등록하기
     */
    public Long UploadCase(CaseRequestDto caseDto, User user) {
        Case newCase = caseDto.toCase(user);
        caseRepository.save(newCase);
        return newCase.getCase_id();
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
    public Optional<List<Case>> findbyMissingName(String keyword, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        return caseRepository.findByMissingName(keyword, sex, age, area);
    }

    public Optional<List<Case>> findbyMissingArea(String keyword, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        return caseRepository.findByMissingArea(keyword, sex, age, area);
    }

    public Optional<List<Case>> findbyFilters(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        return caseRepository.findByFilters(sex, age, area);
    }

    public Optional<List<Case>> sortCasebyTime(){
        return caseRepository.sortCasebyTime();
    }

}
