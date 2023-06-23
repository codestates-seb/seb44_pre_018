package com.e1i5.stackOverflow.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordConfiguration passwordConfiguration;

    public SecurityConfig(PasswordConfiguration passwordConfiguration) {
        this.passwordConfiguration = passwordConfiguration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/member.signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member/login-form") // 로그인 페이지 경로
//                .loginProcessingUrl("/member/login") // 로그인 요청 처리 엔드포인트
//                .defaultSuccessUrl("/home") // 로그인 성공 후 이동할 페이지
//                .failureUrl("/member/login-form?error") // 로그인 실패 시 이동할 페이지
                .permitAll()
                .and()
                .logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordConfiguration.encoder())
                .withUser("user@naver.com")
                .password(passwordConfiguration.encoder().encode("user@naver.com"))
                .roles("USER");
    }
}
