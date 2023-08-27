package com.project.capstonedesign.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.capstonedesign.common.login.handler.CustomAuthFailureHandler;
import com.project.capstonedesign.common.login.handler.CustomAuthSuccessHandler;
import com.project.capstonedesign.common.oauth2.service.CustomOAuth2UserService;
import com.project.capstonedesign.common.oauth2.handler.OAuth2LoginFailureHandler;
import com.project.capstonedesign.common.oauth2.handler.OAuth2LoginSuccessHandler;
import com.project.capstonedesign.common.login.filter.CustomUsernamePasswordAuthenticationFilter;

import com.project.capstonedesign.common.jwt.filter.JwtAuthenticationFilter;
import com.project.capstonedesign.common.jwt.service.JwtService;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import com.project.capstonedesign.common.login.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Spring Security 환경설정
 * 웹 서비스가 로드될때, spring container에 의해 관리 됨.
 * 유저에 대한 인증, 인가에 대한 구성을 Bean 메서드로 주입.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserLoginService userLoginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    /**
     * Http에 대해 인증과 인가를 담당하는 메서드
     * 필터를 통해 인증방식과 인증절차에 대해 등록하며 설정을 담당하는 메서드
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("[+] WebSecurityConfig Start");
        http
                // form 기반의 로그인에 대해 비활성화하며 커스텀으로 구성한 필터 사용
                .formLogin().disable()
                .httpBasic().disable()
                // 서버에 인증정보 저장하지 않음 -> csrf disable
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                // Session 기반의 인증기반을 사용하지 않고 JWT 이용하여 인증
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()

                .antMatchers("/**").permitAll()
                .antMatchers("/join").permitAll()
                .anyRequest().authenticated()
                .and()

                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);

        http.addFilterAfter(customUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomUsernamePasswordAuthenticationFilter.class);

        // 최종 구성값 사용
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * authenticate의 인증 메서드를 제공하는 매니저.
     * Provider의 인터페이스
     * 과정: CustomAuthenticationFilter -> AuthenticationManager(interface) -> CustomAuthenticationProvider(implements)
     *
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userLoginService);
        return new ProviderManager(provider);
    }

    @Bean
    public CustomAuthSuccessHandler customAuthSuccessHandler() {
        return new CustomAuthSuccessHandler(jwtService, userRepository);
    }

    @Bean
    public CustomAuthFailureHandler customAuthFailureHandler() {
        return new CustomAuthFailureHandler();
    }


    /**
     * 커스텀을 수행한 인증 필터로 접근 URL, 데이터 전달방식(form) 등 인증 후 처리에 대한 설정 구성하는 메서드
     *
     * @return CustomAuthenticationFilter
     */
    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordLoginFilter = new CustomUsernamePasswordAuthenticationFilter(objectMapper);
        customUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler());
        customUsernamePasswordLoginFilter.setAuthenticationFailureHandler(customAuthFailureHandler());
        return customUsernamePasswordLoginFilter;
    }

    /**
     * JWT 토큰을 통하여 유저 인증
     * @return JwtAuthorizationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }
}
