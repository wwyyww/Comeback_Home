package TheEarthGuard.ComeBackHome.repository;


import TheEarthGuard.ComeBackHome.domain.Case;
import java.util.List;
import java.util.Optional;

public interface CaseRepository
{
    Case save(Case caseObj); // case만 하면 오류떠서 Obj 붙임
    Optional<Case> findByCaseId(Long case_id);
    Optional<Case> findByUserId(Long finder_id);
    Optional<List<Case>> findByMissingName(String missing_name, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area);
    Optional<List<Case>> findByMissingArea(String missing_area);
    public Optional<List<Case>> findByfilter(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area);
    List<Case> findAll();
}
