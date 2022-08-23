package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.SessionUser;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import TheEarthGuard.ComeBackHome.security.OAuthAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User socialUser = socialUserUpdate(attributes);
        session.setAttribute("user", new SessionUser(socialUser));

        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userRequest));
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oAuth2User));
        } catch (Exception e) {
            System.out.println("exception" + e);
        }


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }

    private User socialUserUpdate(OAuthAttributes attributes) {
        User user2 = new User();

//        User user = userRepository.findByEmail(attributes.getEmail()).map(entity->entity.updateSocialUser(attributes.getUsername(), attributes.getEmail()))
//                .orElse(attributes.toEntity());
//        User user = userRepository.findByEmail(attributes.getEmail()).updateSocialUser(attributes.getUsername(), attributes.getEmail());
//        User user = userRepository.findByEmail(attributes.getEmail());
        user2.setEmail(attributes.getEmail());
        user2.setName(attributes.getName());
        return userRepository.save(user2);

//        if (user == null) {
//            user2 = attributes.toEntity();
//            return userRepository.save(user2);
//        }else{
//            log.info("user : "+user.getName()+"   user2 : "+user2.getName());
//            return user;
//        }

    }




}

