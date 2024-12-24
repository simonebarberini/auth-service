package com.poc.authservice.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtHandler implements JwtHandler<Jws<Claims>> {
    @Override
    public Jws<Claims> onPlaintextJwt(Jwt<Header, String> jwt) {
        return null;
    }

    @Override
    public Jws<Claims> onClaimsJwt(Jwt<Header, Claims> jwt) {
        return null;
    }

    @Override
    public Jws<Claims> onPlaintextJws(Jws<String> jws) {
        return null;
    }

    @Override
    public Jws<Claims> onClaimsJws(Jws<Claims> jws) {
        System.out.println("JWS claims: " + jws.getBody());
        return jws;
    }
}
