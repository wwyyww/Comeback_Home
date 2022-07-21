package TheEarthGuard.ComeBackHome.service;

import static java.util.Optional.ofNullable;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입하기() throws Exception{
        User user = new User();
        user.setEmail("aa@naver.com");
        user.setPw("1234");
        userService.signUp(user);

        Optional<User> findUser = ofNullable(userService.findByEmail(user.getEmail()));
        Assertions.assertEquals(user.getEmail(), findUser.get().getEmail());
    }

}
