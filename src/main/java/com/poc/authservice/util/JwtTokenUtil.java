package com.poc.authservice.util;

import com.poc.authservice.model.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

//    public AuthResponse validateToken(String token){
//        try {
//            Claims claims = extractClaims(token);
//            AuthResponse response = new AuthResponse();
//            response.setAtuht(true);
//            response.setRuolo(claims.get("ruolo",String.class));
//            response.setCodiceAbi(claims.get("codiceAbi", String.class));
//            response.setToken(token);
//            response.setUsername(claims.getSubject());
//            return response;
//        }catch (JwtException | IllegalArgumentException e){
//            return new AuthResponse(null, null, null, null, false);
//        }
//    }


    public boolean validateToken(String token){
        try {
            Claims claims = extractClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
