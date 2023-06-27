package com.e1i5.stackOverflow.auth.filter;

import com.e1i5.stackOverflow.auth.jwt.JwtTokenizer;
import com.e1i5.stackOverflow.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 *클라이언트 측에서 JWT를 이용해 자격 증명이 필요한 리소스에 대한 request 전송 시, request header를 통해 전달받은 JWT를 서버 측에서 검증하는 기능을 구현
 * */

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, // JwtTokenizer는 JWT를 검증하고 Claims(토큰에 포함된 정보)를 얻는 데 사용됩니다.
                                 CustomAuthorityUtils authorityUtils) {  // CustomAuthorityUtils는 JWT 검증에 성공하면 Authentication 객체에 채울 사용자의 권한을 생성하는 데 사용됩니다.
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Map<String, Object> claims = verifyJws(request); //JWT를 검증하는 데 사용되는 private 메서드
//        setAuthenticationToContext(claims);      // Authentication 객체를 SecurityContext에 저장하기 위한 private 메서드
//
//        filterChain.doFilter(request, response); //문제없이 JWT의 서명 검증에 성공하고, Security Context에 Authentication을 저장한 뒤에는 (5다음(Next) Security Filter를 호출.
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);// security filter 호출
    }

    // (6)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");  // Authorization header의 값을 얻은 후에

        return authorization == null || !authorization.startsWith("Bearer");  // Authorization header의 값이 null이거나 Authorization header의 값이 “Bearer”로 시작하지 않는다면 해당 Filter의 동작을 수행하지 않도록 정의합니다.
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // request의 header에서 JWT를 얻고 있습니다.
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); //JWT 서명(Signature)을 검증하기 위한 Secret Key를 얻습니다. > 여기서 memberId를 얻을 수 있을 것 같다.
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   //JWT에서 Claims를 파싱 합니다. > 내부적으로 서명검증에 성공했음을 의미.

        return claims;
    }
    /**
     * (3-1)에서는 request의 header에서 JWT를 얻고 있습니다.
     *
     * 이전 챕터에서 클라이언트의 로그인 인증이 성공적으로 수행되면, 서버 측에서 Authorization header에 JWT를 추가했던 로직을 떠올려 보기 바랍니다.
     *
     * 💡 이전 챕터에서 Authorization header에 추가된 JWT는 response header에 포함된 것이고, (3-1)에서의 JWT는 클라이언트가 response header로 전달받은 JWT를 request header에 추가해서 서버 측에 전송한 것이라는 사실을 잊지 마세요!
     *
     * 그리고 String 클래스의 replace() 메서드를 이용해 “Bearer “부분을 제거합니다.
     *
     * 💡 (3-1)에서 변수명을 jws로 지정한 이유는 서명된 JWT를 JWS(JSON Web Token Signed)라고 부르기 때문이라는 점 참고하세요.
     *
     * (3-2)에서는 JWT 서명(Signature)을 검증하기 위한 Secret Key를 얻습니다.
     *
     * (3-3)에서는 JWT에서 Claims를 파싱 합니다.
     *
     * 여기서 기억해야 할 부분은 JWT에서 Claims를 파싱 할 수 있다는 의미는 내부적으로 서명(Signature) 검증에 성공했다는 의미입니다.
     *
     * ⭐ 즉, verify() 같은 검증 메서드가 따로 존재하는 것이 아니라 Claims가 정상적으로 파싱이 되면 서명 검증 역시 자연스럽게 성공했다는 사실을 꼭 기억하기 바랍니다.
     * */

    private void setAuthenticationToContext(Map<String, Object> claims) {
//        long memberId = (long) claims.get("memberId");
        String username = (String) claims.get("username");   //1
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));  // 2
//        UserDetails userDetails = authorityUtils.
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);  // 3
        SecurityContextHolder.getContext().setAuthentication(authentication);
        /**
         * 1에서는 JWT에서 파싱 한 Claims에서 username을 얻습니다.
         *
         * 2에서는 JWT의 Claims에서 얻은 권한 정보를 기반으로 List<GrantedAuthority를 생성합니다.
         *
         * 3에서는 username과 List<GrantedAuthority를 포함한 Authentication 객체를 생성합니다.
         *
         * 4에서는 SecurityContext에 Authentication 객체를 저장합니다.
         *
         * // UserDetails 객체에는 사용자 이름, 비밀번호(비워둘 수 있다.), 권한 정보가 포함됨.
         * */
        System.out.println("MemberId in SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication().getDetails());

    }

}
