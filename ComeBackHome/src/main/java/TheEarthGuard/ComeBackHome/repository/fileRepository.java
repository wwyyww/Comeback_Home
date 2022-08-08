package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface fileRepository extends JpaRepository<FileEntity, Long> {

}
