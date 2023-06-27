package com.e1i5.stackOverflow.auth.filter;

import com.e1i5.stackOverflow.auth.dto.LoginDto;
import com.e1i5.stackOverflow.auth.jwt.JwtTokenizer;
import com.e1i5.stackOverflow.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer){
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    //내부에서 인증 시도하는 로직 구현
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper(); // 클라이언트에서 전송한 Username과 Password를 DTO 클래스로 역직렬화(Deserialization) 하기 위해 ObjectMapper 인스턴스를 생성
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); // ServletInputStream을 LoginDto 클래스의 객체로 역직렬화(Deserialization)합니다.

        // Username과 Password 정보를 포함한 UsernamePasswordAuthenticationToken을 생성합니다.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken); // UsernamePasswordAuthenticationToken을 AuthenticationManager에게 전달하면서 인증 처리를 위임합니다.
    }

    // 클라이언트의 인증 정보를 이용해 인증에 성공할 경우 호출 **
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException{
        Member member = (Member) authResult.getPrincipal(); // authResult.getPrincipal()로 Member 엔티티 클래스의 객체를 얻습니다.
// AuthenticationManager 내부에서 인증에 성공하면 인증된 Authentication 객체가 생성되면서 principal 필드에 Member 객체가 할당됩니다.


        String accessToken = delegateAccessToken(member); // delegateAccessToken(member) 메서드를 이용해 Access Token을 생성합니다.
        String refreshToken = delegateRefreshToken(member); // delegateRefreshToken(member) 메서드를 이용해 Refresh Token을 생성합니다.

        response.setHeader("Authorization", "Bearer " + accessToken); // response header(Authorization)에 Access Token을 추가합니다. Access Token은 클라이언트 측에서 백엔드
        // 애플리케이션 측에 요청을 보낼 때마다 request header에 추가해서 클라이언트 측의 자격을 증명하는 데 사용됩니다.
        response.setHeader("Refresh", refreshToken); // response header(Refresh)에 Refresh Token을 추가합니다. Refresh Token은 Access Token이 만료될 경우,
        // 클라이언트 측이 Access Token을 새로 발급받기 위해 클라이언트에게 추가적으로 제공될 수 있으며 Refresh Token을 Access Token과 함께 클라이언트에게 제공할지 여부는 애플리케이션의 요구 사항에 따라 달라질 수 있습니다.

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);  // 추가 > V3에서 적용, AuthenticationSuccessHandler의 onAuthenticationSuccess() 메서드를 호출
    }

    // 토큰을 생성하는 구체적인 로직
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());  // 식별자도 포함.
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    //
    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }


}
