package com.e1i5.stackOverflow.auth.interceptor;

import com.e1i5.stackOverflow.auth.jwt.JwtTokenizer;
import com.e1i5.stackOverflow.auth.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenizer jwtTokenizer;
    private static final ThreadLocal<Long> authenicatedMemberId = new ThreadLocal<>();


    public JwtInterceptor(JwtTokenizer jwtTokenizer){
        this.jwtTokenizer = jwtTokenizer;
    }

    // request의 memberId 반환 > controller에서 사용
    public static long requestMemberId(){
        Long memberId = authenicatedMemberId.get();
        if (memberId == null) {
            throw new IllegalStateException("인증된 memberId를 찾을 수 없습니다.");
        }
        return memberId;
    }

    // 핸들러가 실행되기 전에 실행. 공통적으로 적용할 사항
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        try {
            String jws = request.getHeader("Authorization").replace("Bearer", "");
            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT 서명(Signature)을 검증하기 위한 Secret Key를 얻습니다.
            Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody(); // JWT에서 Claims를 파싱합니다.

            Long memberId = Long.parseLong(claims.get("memberId").toString());
            if(memberId != null) {
                authenicatedMemberId.set(memberId);
                return true;
            }
            else {
                ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);
                return false;
            }

        }catch (Exception ex){//
            authenicatedMemberId.set(null); // 로직 실현 불가능
            return true;

        }
    }

    // 핸들러가 실행된 이후에 실행되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        authenicatedMemberId.remove();
    }

    // 핸들러 이후에 실행 . 비즈니스 로직의 예외, 리소스 정리에 사용
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{

    }

}
