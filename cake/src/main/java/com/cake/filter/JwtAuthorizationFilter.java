package com.cake.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cake.model.Member;
import com.cake.oauth.PrincipalDetails;
import com.cake.service.MemberService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberService memberService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService) {
        super(authenticationManager);
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청 됨");
        String jwtHeader = request.getHeader("Authorization");

        if(jwtHeader ==null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request,response);
            return;
        }
        String jwtToken = jwtHeader.replace("Bearer ","");

        String username = JWT.require(Algorithm.HMAC512("fus_ro_dah")).build().verify(jwtToken).getClaim("username").asString();

        if(username != null) {
            Optional<Member> memberEntity = memberService.findByUsername(username);
            Member member = memberEntity.orElseThrow(() -> new RuntimeException());

            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);


        }


        super.doFilterInternal(request, response, chain);
    }
}
