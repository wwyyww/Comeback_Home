package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Role;
import TheEarthGuard.ComeBackHome.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String pw;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "성별은 필수 입력값입니다.")
    private Integer sex;

    @NotBlank(message = "생년월일은 필수 입력값입니다.")
    private String birth;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phone;

    @Builder
    public UserDto(String email, String pw, String name, Integer sex, String birth, String phone) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
    }

    public User toUser(String encryptPw) {
        return User.builder()
                .email(this.email)
                .pw(this.pw)
                .name(this.name)
                .phone(this.phone)
                .birth(this.birth)
                .sex(this.sex)
                .role(Role.USER.toString())
                .warning_cnt(0)
                .build();
    }


}
