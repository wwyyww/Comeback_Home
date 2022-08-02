package TheEarthGuard.ComeBackHome.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
@Getter
@Setter
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String pw;

    private String name;

    private Integer sex;

    private String birth;

    private String phone;

    private String role;
    private Integer warning_cnt;
    private Integer fail_cnt;

    @Builder
    public User(Long id, String email, String pw, String name, Integer sex, String birth, String phone, String role, Integer warning_cnt, Integer fail_cnt) {
        this.id = id;
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
        this.role = role;
        this.warning_cnt = warning_cnt;
        this.fail_cnt = fail_cnt;
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
                .role(Role.USER.toString())
                .warning_cnt(0)
                .fail_cnt(0)
                .build();
    }

    public void updateFailCount(Integer fail_cnt) {
        this.fail_cnt = fail_cnt;
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
