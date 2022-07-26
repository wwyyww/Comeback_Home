package TheEarthGuard.ComeBackHome.repository;


import TheEarthGuard.ComeBackHome.domain.Case;
import java.util.List;
import java.util.Optional;

public interface CaseRepository
{
    Case save(Case caseObj); // case만 하면 오류떠서 Obj 붙임
    Optional<Case> findByCaseId(Long case_id);
    Optional<Case> findByFinderId(String finder_id);
    Optional<Case> findByMissingName(String missing_name);
    List<Case> findAll();
}
