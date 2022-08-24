package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<List<Report>> findAllByUserOrderByWitnessTimeDesc(User user);

    Optional<List<Report>> findAllByCasesOrderByWitnessTimeDesc(Case cases);

    Optional<List<Report>> findAllByOrderByWitnessTimeDesc();
    //ByMissingTimeStartOrderByWitnessTimeDesc();

   Optional<List<Report>> findByWitnessRegion(String region);

   Optional<List<Report>> findByWitnessTimeBetween(LocalDateTime start, LocalDateTime end);

   Optional<List<Report>> findByWitnessRegionAndWitnessTimeBetween(String region, LocalDateTime start, LocalDateTime end);

   List<Report> findAll();
   Page<Report> findAll(Pageable pageable);
}
