package pjsassy.mbtichatclon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pjsassy.mbtichatclon.jwt.JwtFilter;
import pjsassy.mbtichatclon.jwt.TokenProvider;
import pjsassy.mbtichatclon.user.service.RedisUtil;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    //TokenProvider 를 주입 받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider, redisUtil);
        httpSecurity.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
