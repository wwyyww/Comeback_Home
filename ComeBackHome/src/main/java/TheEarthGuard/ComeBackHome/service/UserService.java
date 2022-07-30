package TheEarthGuard.ComeBackHome.service;


import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.UserAdapter;
import TheEarthGuard.ComeBackHome.dto.UserDto;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    private final KakaoOAuth2 kakaoOAuth2;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signUp(UserDto userDto) {
        String encryptPw = passwordEncoder.encode(userDto.getPw());
        User newUser = userDto.toUser(encryptPw);
        userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        System.out.println("EMAIL : " + email);
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    
    //유효성 검사
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    public void checkEmail(User user) {
        boolean emailDuplicate = userRepository.existsByEmail(user.getEmail());
        if (emailDuplicate) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return new UserAdapter(user);
    }

    public void kakaoLogin(String code) {

    }

}
