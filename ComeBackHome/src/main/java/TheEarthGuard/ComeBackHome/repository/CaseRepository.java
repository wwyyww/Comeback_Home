package TheEarthGuard.ComeBackHome.repository;


import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CaseRepository")
public interface CaseRepository extends JpaRepository<Case, Long>, CustomCaseRepository<Case> {
    Optional<List<Case>> findAllByUser (User user);
    Optional<Case> findByCaseId(Long caseId);
}
