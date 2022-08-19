package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Warn;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarnRepository extends JpaRepository<Warn, Long> {
    @EntityGraph(attributePaths = {"warns"})
    List<Warn> findAll();
}
