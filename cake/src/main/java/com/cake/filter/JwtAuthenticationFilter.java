package com.cake.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cake.model.Member;
import com.cake.oauth.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//Jwt로 로그인 처리를 하는 필터이다.

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("#login 시도========================");

        try{
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);//json으로 입력하면 java객체로 바꿈
            UsernamePasswordAuthenticationToken authenticationToken
                    =new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword()); //usernamepasswordtoken을 만듬(깡통)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);//매니저한테 넣어서 비교
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();//principaldetailsservice의 loadByuser에 넣어서 인증확인
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("#SuccessfulAuthentication=================");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("fus ro dah")
                .withExpiresAt(new Date(System.currentTimeMillis() + 60*1000*10))
                .withClaim("id",principalDetails.getMember().getId())
                .withClaim("username",principalDetails.getMember().getUsername())
                .sign(Algorithm.HMAC512("fus_ro_dah"));
        response.addHeader("Authorization","Bearer "+jwtToken);
    }
}


//    BufferedReader br = request.getReader();
//getReader() returns a BufferedReader that will allow you to read the body of the request.
//request body를 읽는것을 허용
