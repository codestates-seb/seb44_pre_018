//package com.e1i5.stackOverflow.auth.handler;
//
//import com.e1i5.stackOverflow.auth.jwt.JwtTokenizer;
//import com.e1i5.stackOverflow.auth.utils.CustomAuthorityUtils;
//import com.e1i5.stackOverflow.member.entity.Member;
//import com.e1i5.stackOverflow.member.service.MemberService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URI;
//import java.util.*;
//
//
//public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final JwtTokenizer jwtTokenizer;
//    private final CustomAuthorityUtils authorityUtils;
//    private final MemberService memberService;
//
//    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
//                                      CustomAuthorityUtils authorityUtils,
//                                      MemberService memberService) {
//        this.jwtTokenizer = jwtTokenizer;
//        this.authorityUtils = authorityUtils;
//        this.memberService = memberService;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        var oAuth2User = (OAuth2User)authentication.getPrincipal();
//        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
//        List<String> authorities = authorityUtils.createRoles(email);
//
//        saveMember(email);  // (5)
//        redirect(request, response, email, authorities);  // (6)
//    }
//
//    //이메일의 제외한 나머지 값을 랜덤으로 멤버 생성
//    private void saveMember(String email) {
//        int nameLength = 100;
//        int phoneLength = 13;
//        int passwordLength = 100;
//
//        String randomName = generateRandomString(nameLength);
//        String randomPhone = generateRandomString(phoneLength);
//        String randomPassword = generateRandomString(passwordLength);
//
//        Member member = new Member(randomName, randomPhone, email, randomPassword);
//
//        memberService.signupMember(member);
//    }
//
//    //제약조건에 맞게 문자열을 생성하는 함수
//    private String generateRandomString(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder sb = new StringBuilder(length);
//
//        for (int i = 0; i < length; i++) {
//            int randomIndex = (int) (Math.random() * characters.length());
//            char randomChar = characters.charAt(randomIndex);
//            sb.append(randomChar);
//        }
//
//        return sb.toString();
//    }
//
//    private void redirect(HttpServletRequest request, HttpServletResponse response, String username, List<String> authorities) throws IOException {
//        String accessToken = delegateAccessToken(username, authorities);  // (6-1)
//        String refreshToken = delegateRefreshToken(username);     // (6-2)
//
//        String uri = createURI(accessToken, refreshToken).toString();   // (6-3)
//        getRedirectStrategy().sendRedirect(request, response, uri);   // (6-4)
//    }
//
//    private String delegateAccessToken(String username, List<String> authorities) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("username", username);
//        claims.put("roles", authorities);
//
//        String subject = username;
//        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
//
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//
//        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
//
//        return accessToken;
//    }
//
//    private String delegateRefreshToken(String username) {
//        String subject = username;
//        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//
//        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
//
//        return refreshToken;
//    }
//
//    //**************** http 수정 가능성 ***********
//    private URI createURI(String accessToken, String refreshToken) {
//        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
//        queryParams.add("access_token", accessToken);
//        queryParams.add("refresh_token", refreshToken);
//
//        return UriComponentsBuilder
//                .newInstance()
//                .scheme("http")
//                .host("localhost")
////                .port(80)
//                .path("/receive-token.html")
//                .queryParams(queryParams)
//                .build()
//                .toUri();
//    }
//
//}
