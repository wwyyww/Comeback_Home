package TheEarthGuard.ComeBackHome;




import TheEarthGuard.ComeBackHome.security.CustomAuthFailureHandler;
import TheEarthGuard.ComeBackHome.security.CustomAuthSuccessHandler;
import TheEarthGuard.ComeBackHome.service.CustomOAuth2UserService;
import TheEarthGuard.ComeBackHome.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @SuppressFBWarnings(justification = "Generated code")
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
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/reports/**").authenticated()
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/users/login").permitAll()
                .loginProcessingUrl("/login_proc")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
                .cors().disable()
                .headers().frameOptions().disable().and();



        return http.build();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/","/css/**","/assets/**","/plugins/**","/images/**")
                .permitAll();
    }

    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**");
    }


}
