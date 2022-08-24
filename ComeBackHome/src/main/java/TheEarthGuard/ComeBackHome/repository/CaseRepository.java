package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long>, CaseCustomRepository {

    Optional<Case> findByCaseId(Long case_id);
    Optional<List<Case>> findAllByUser(User user);
    Optional<List<Case>> findByMissingTimeStartOrderByMissingTimeStartDesc(LocalDateTime missingTimeStart);




    @Modifying
    @Query("update Case c set c.hitCnt = c.hitCnt + 1 where c.caseId = :caseId")
    int updateHitCase(@Param("caseId") Long caseId);
}
