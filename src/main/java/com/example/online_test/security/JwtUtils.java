package com.example.online_test.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.token.secret}")
    private String mySecretKey;

    @Value("${jwt.token.validity}")
    private Long jwtExpireDate;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Claims claims=Jwts.claims().setSubject(principal.getUsername())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+ jwtExpireDate));
           claims.put("userId", String.valueOf(principal.getUsername()));
           claims.put("role", principal.getAuthorities());

        byte[] base64s=Base64.getEncoder().encode(mySecretKey.getBytes());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, base64s)
                .compact();

//        return Jwts.builder()
//                .setSubject((principal.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + jwtExpireDate))
//                .signWith(SignatureAlgorithm.PS512, mySecretKey)
//                .compact();
//        claims.put("userId", String.valueOf(principal.getId()));
//        claims.put("role", principal.getAuthorities());

//        byte[] encode = Base64.getEncoder().encode(jwtSecret.getBytes());
//        return Jwts.builder().setClaims(claims)
//                .signWith(SignatureAlgorithm.ES512, base64s)
//                .compact();
    }
    public String getPhoneNumberFromJwtToken(String token){
        return Jwts.parser().setSigningKey(mySecretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(mySecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }



}
