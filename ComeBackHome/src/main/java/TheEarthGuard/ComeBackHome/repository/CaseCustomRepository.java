package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import java.util.List;
import java.util.Optional;

public interface CaseCustomRepository {

    Optional<List<Case>> searchByMissingName(String missing_name, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area, String feature);
    Optional<List<Case>> searchByMissingArea(String missing_area, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area, String feature);
    Optional<List<Case>> searchByFilters(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area, String feature);
    Optional<List<Case>> casebyTime();
}
