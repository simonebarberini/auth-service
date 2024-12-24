package com.poc.authservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
@Component
public class JwtTokenUtil {

    @Autowired
    CustomJwtHandler customJwtHandler;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public Claims extractClaims(String token){
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parse(token, customJwtHandler)
                .getBody();
    }

    public boolean validateToken(String token){
        try {
            Claims claims = extractClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
