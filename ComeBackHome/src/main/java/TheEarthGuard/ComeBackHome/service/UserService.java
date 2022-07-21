package TheEarthGuard.ComeBackHome.service;


import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signUp(User user) {
        String encryptPw = passwordEncoder.encode(user.getPw());
        user.setPw(encryptPw);
        userRepository.save(user.create(user));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
