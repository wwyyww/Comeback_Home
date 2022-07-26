package TheEarthGuard.ComeBackHome.service;


import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final KakaoOAuth2 kakaoOAuth2;

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

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return user;    }

    public void kakaoLogin(String code) {

    }

}
