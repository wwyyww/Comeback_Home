package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseSaveRequestDto;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
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
    private final FileHandler fileHandler;

    public CaseService(CaseRepository caseRepository,
        FileHandler fileHandler) {
        this.caseRepository = caseRepository;
        this.fileHandler = fileHandler;
    }

    /**
     * 사건 등록하기
     */
    public Long UploadCase(CaseSaveRequestDto caseDto,  List<MultipartFile> files) throws Exception{
        Case newCase = caseDto.toEntity();

        List<FileEntity> missing_pictures = fileHandler.parseFileInfo(files);
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
     * 전체 사건 조회
     */
    public List<Case> getCaseList() {
        return caseRepository.findAll();
    }

    /**
     *  사건 하나 조회 (사용자 id 기반)
     */
    public Optional<List<Case>> findCaseByUser(User user) {
        return caseRepository.findByUser(user);
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
//    public Optional<List<Case>> findbyMissingName(String keyword, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
//        return caseRepository.findByMissingNameFilter(keyword, sex, age, area);
//    }
//
//    public Optional<List<Case>> findbyMissingArea(String keyword){
//        return caseRepository.findByMissingAreaFilter(keyword);
//    }

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
}
