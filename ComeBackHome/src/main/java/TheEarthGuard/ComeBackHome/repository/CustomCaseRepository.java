package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomCaseRepository<T> {
//    Optional<Case> findByFinderId(String finder_id);
    Optional<List<Case>> searchByMissingName(String missing_name, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area);
    Optional<List<Case>> searchByMissingArea(String missing_area, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area);
    Optional<List<Case>> searchByFilters(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area);
    Optional<List<Case>> casebyTime();
    List<Case> findAll();
}
