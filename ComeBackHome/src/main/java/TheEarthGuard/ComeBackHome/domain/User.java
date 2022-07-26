package TheEarthGuard.ComeBackHome.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
@Table
@Getter
@Setter
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}",
//            message = "비밀번호는 영문과 숫자 조합으로 8 ~ 16자리까지 가능합니다.")
    private String pw;
    private String name;
    private Integer sex;
    private String birth;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Integer warning_cnt;

    @Builder
    public User(Long id, String email, String pw, String name, Integer sex, String birth, String phone, Role role, Integer warning_cnt) {
        this.id = id;
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
        this.role = role;
        this.warning_cnt = warning_cnt;
    }

    public User() {

    }

    public User create(User user) {
        return User.builder()
                .email(user.email)
                .pw(user.pw)
                .name(user.name)
                .phone(user.phone)
                .birth(user.birth)
                .sex(user.sex)
                .role(Role.USER)
                .warning_cnt(0)
                .build();
    }

    public String getRoleValue() {
        return this.role.getValue();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
