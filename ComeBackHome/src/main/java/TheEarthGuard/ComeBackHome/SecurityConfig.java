package TheEarthGuard.ComeBackHome;




import TheEarthGuard.ComeBackHome.security.CustomAuthFailureHandler;
import TheEarthGuard.ComeBackHome.security.CustomAuthSuccessHandler;
import TheEarthGuard.ComeBackHome.service.CustomOAuth2UserService;
import TheEarthGuard.ComeBackHome.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(UserService userService, CustomOAuth2UserService customOAuth2UserService) {
        this.userService = userService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthSuccessHandler(userService);
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomAuthFailureHandler(userService);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/users/login").permitAll()
                .loginProcessingUrl("/login_proc")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable().and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);



        return http.build();
    }


}
