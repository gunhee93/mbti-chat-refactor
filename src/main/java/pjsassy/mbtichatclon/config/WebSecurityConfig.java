package pjsassy.mbtichatclon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import pjsassy.mbtichatclon.jwt.TokenProvider;
import pjsassy.mbtichatclon.jwt.accessRestriction.JwtAccessDeniedHandler;
import pjsassy.mbtichatclon.jwt.accessRestriction.JwtAuthenticationEntryPoint;
import pjsassy.mbtichatclon.user.service.RedisUtil;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtSecurityConfig(tokenProvider, redisUtil))
                .and()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers("/", "/**", "/users/**").permitAll()
                .anyRequest().permitAll() // 나머지 api 는 모두 인증 필요
                .and().build();
    }
}
