package TheEarthGuard.ComeBackHome.service;


import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.UserAdapter;
import TheEarthGuard.ComeBackHome.dto.UserDto;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;


@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final CaseService caseService;
    private final ReportService reportService;


    @Autowired
    private PasswordEncoder passwordEncoder;

//    private final KakaoOAuth2 kakaoOAuth2;

    public UserService(UserRepository userRepository, CaseService caseService, ReportService reportService) {
        this.userRepository = userRepository;
        this.caseService = caseService;
        this.reportService = reportService;
    }

    @Transactional
    public void signUp(UserDto userDto) {
        String encryptPw = passwordEncoder.encode(userDto.getPw());
        User newUser = userDto.toUser(encryptPw);
        userRepository.save(newUser);
    }

    public User findByEmail(String email) {
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

    public Boolean validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }

    public void addFailCnt(User user) {
        User targetUser = findByEmail(user.getEmail());
        targetUser.updateFailCount(targetUser.getFail_cnt()+1);
        userRepository.saveAndFlush(targetUser);
    }

    @Transactional
    public Long updateUser(@CurrentUser User user, UserDto userDto) {
        User updateUser = userRepository.findByEmail(user.getEmail());
        updateUser.update(userDto.getEmail(), userDto.getName(), userDto.getBirth(), userDto.getPhone());

        userRepository.save(updateUser);

        return user.getId();
    }

    @Transactional
    public void deleteUser(Long id, User user) {
        Optional<User> deleteUser = userRepository.findById(id);
        if (user.getRole().equals("ADMIN")) {
            Optional<List<Case>> cases = caseService.findCaseByUser(deleteUser.get());
            List<Report> reports = reportService.getReportsListByUser(deleteUser.get());

            for (Report report : reports) {
                reportService.deleteReport(report.getId(), report.getUser());
            }

            for (Case caseEntity : cases.get()) {
                caseService.deleteCase(caseEntity.getCaseId(), caseEntity.getUser());
            }

            userRepository.delete(deleteUser.get());

        } else {
            new IllegalArgumentException("회원 삭제 실패 : 관리자가 아님");
        }


    }

    public List<User> getUserList() {
        return userRepository.findAll();
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
