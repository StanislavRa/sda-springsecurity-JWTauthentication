package com.sda.authentificationdemo.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author StanislavR
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMill;

    @Qualifier("jwtUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, Collection<? extends GrantedAuthority> grantedAuthorities) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(grantedAuthorities));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMill);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secret)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    //    public String resolveToken(HttpServletRequest req) {
//        String bearerToken = req.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7, bearerToken.length());
//        }
//        return null;
//    }
//public String resolveToken(HttpServletRequest req) {
//    String bearerToken = req.getHeader("Authorization");
//    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//        return bearerToken.substring(7, bearerToken.length());
//    }
//    return null;
//}
    public String resolveToken(HttpServletRequest req) {

        Cookie cookie = WebUtils.getCookie(req, "access_token");

        if (cookie == null) {
            return null;
        }

        return cookie.getValue();

    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return false;


        }
    }

    private List<String> getRoleNames(Collection<? extends GrantedAuthority> grantedAuthorities) {
        List<String> result = new ArrayList<>();

        grantedAuthorities.forEach(grantedAuthority -> result.add(grantedAuthority.getAuthority()));
        return result;
    }

}
