package TheEarthGuard.ComeBackHome.repository;


import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CaseRepository extends JpaRepository<Case, Long>, CaseCustomRepository<Case>{
    Optional<List<Case>> findByUser (User user);
    Optional<Case> findByCaseId(Long caseId);
}
