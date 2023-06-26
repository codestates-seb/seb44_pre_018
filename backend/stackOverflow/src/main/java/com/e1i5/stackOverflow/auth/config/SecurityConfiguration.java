package com.e1i5.stackOverflow.auth.config;


import com.e1i5.stackOverflow.auth.filter.JwtAuthenticationFilter;
import com.e1i5.stackOverflow.auth.filter.JwtVerificationFilter;
import com.e1i5.stackOverflow.auth.handler.MemberAccessDeniedHandler;
import com.e1i5.stackOverflow.auth.handler.MemberAuthenticationEntryPoint;
import com.e1i5.stackOverflow.auth.handler.MemberAuthenticationFailureHandler;
import com.e1i5.stackOverflow.auth.handler.MemberAuthenticationSuccessHandler;
import com.e1i5.stackOverflow.auth.jwt.JwtTokenizer;
import com.e1i5.stackOverflow.auth.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils){
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults()) // corsConfigurationSource라는 이름으로 등록된 Bean을 이용 = CorsFilter를 적용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize // > 잘 작동하지 않는 듯
                        .antMatchers(HttpMethod.POST, "/member/signup").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/member/update").hasRole("USER") // 회원수정
                        .antMatchers(HttpMethod.GET, "/member").hasRole("ADMIN") // getMebers
                        .antMatchers(HttpMethod.GET, "/member/getmember").hasAnyRole("USER", "ADMIN")// getMember
                        .antMatchers(HttpMethod.DELETE, "/member/delete").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/question/create").hasRole("USER") //질문생성
                        .antMatchers(HttpMethod.POST, "/question/**").hasRole("USER") //질문투표
                        .antMatchers(HttpMethod.PATCH, "/question/update/**").hasRole("USER") //질문 수정
                        .antMatchers(HttpMethod.DELETE, "/question/delete/**").hasRole("USER") //질문 삭제
                        .antMatchers(HttpMethod.POST, "/v1/comment/create").hasAnyRole("USER","ADMIN") //댓글 생성
                        .antMatchers(HttpMethod.PATCH,"/v1/comment/update/**").hasRole("USER") //댓글 수정
                        .antMatchers(HttpMethod.DELETE,"/v1/comment/delete/**").hasRole("USER") //댓글 삭제
                        .anyRequest().permitAll()
                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));// 모든 출처(Origin)에 대해 스크립트 기반의 HTTP 통신을 허용하도록 설정
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE"));// 파라미터로 지정한 HTTP Method에 대한 HTTP 통신을 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // CorsConfigurationSource 인터페이스의 구현 클래스인 UrlBasedCorsConfigurationSource 클래스의 객체를 생성
        source.registerCorsConfiguration("/**", corsConfiguration);  // 모든 URL에 앞에서 구성한 CORS 정책(CorsConfiguration)을 적용
        return source;
    }

    // 추가2 우리가 구현한 JwtAuthenticationFilter를 등록하는 역할
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        //2-1 AbstractHttpConfigurer를 상속해서 Custom Configurer를 구현 AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity>와 같이 AbstractHttpConfigurer를 상속하는 타입과 HttpSecurityBuilder를 상속하는 타입을 제너릭 타입으로 지정
        @Override
        public void configure(HttpSecurity builder) throws Exception {// 2-2 configure() 메서드를 오버라이드해서 Configuration을 커스터마이징
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);// 2-3 getSharedObject(AuthenticationManager.class)를 통해 AuthenticationManager의 객체를 얻을 수 있습니다.
            //  getSharedObject()를 통해서 Spring Security의 설정을 구성하는 SecurityConfigurer 간에 공유되는 객체를 얻을 수 있습니다.
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer); // 2-4 JwtAuthenticationFilter를 생성하면서 JwtAuthenticationFilter에서 사용되는 AuthenticationManager와 JwtTokenizer를 DI
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login"); //2-5 setFilterProcessesUrl() 메서드를 통해 디폴트 request URL인 “/login”을 “/auth/login”으로 변경
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());  // 핸들러 등록
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());  // 핸들러 등록

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);

            // addFilter() 메서드를 통해 JwtAuthenticationFilter를 Spring Security Filter Chain에 추가

        }
    }

}
