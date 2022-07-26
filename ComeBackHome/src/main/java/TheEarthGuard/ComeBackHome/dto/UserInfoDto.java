package TheEarthGuard.ComeBackHome.dto;


import TheEarthGuard.ComeBackHome.domain.User;
import lombok.Getter;

@Getter
public class UserInfoDto{
    private String name;
    private String email;
    private String picture;

    public UserInfoDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
