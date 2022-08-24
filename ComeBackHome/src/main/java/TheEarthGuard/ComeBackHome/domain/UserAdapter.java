package TheEarthGuard.ComeBackHome.domain;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    @SuppressFBWarnings(justification = "Generated code")
    public UserAdapter(User user) {
        super(user.getUsername(), user.getPw(), List.of(new SimpleGrantedAuthority(user.getRole())));
        this.user = user;
    }

    public static UserAdapter from(User user) {
        return new UserAdapter(user);
    }





}
