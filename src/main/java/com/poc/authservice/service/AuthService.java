package com.poc.authservice.service;

import com.poc.authservice.model.AuthResponse;
import com.poc.authservice.model.User;
import com.poc.authservice.repo.UserRepository;
import com.poc.authservice.util.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;



    @Value("${jwt.expiration}")
    private long jwtExpiration;



    public AuthResponse authenticate(String username, String password, String codiceAbi) {
        Optional<User> user = userRepository.findByUsernameAndCodiceAbi(username, codiceAbi);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            AuthResponse response = new AuthResponse();
            response.setUsername(username);
            //response.setRuolo(); da aggiungere in fase di creazione dell'utente
            response.setCodiceAbi(codiceAbi);
            response.setToken(generateToken(user.get()));
            return response;

        }
        throw new RuntimeException("Credenziali non valide");
    }

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("codiceAbi", user.getCodiceAbi())
                .claim("ruolo", user.getRuolo())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public ResponseEntity<Boolean> validateToken(String authHeader) {
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        String token = authHeader.substring(7);
        boolean isValid = jwtTokenUtil.validateToken(token);
        if (isValid){
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}
