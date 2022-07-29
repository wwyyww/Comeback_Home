package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    //    Optional<User> findById(Long id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAll();
}
