package com.poc.authservice.service;

import com.poc.authservice.model.User;
import com.poc.authservice.repo.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;



    @Value("${jwt.expiration}")
    private long jwtExpiration;



    public String authenticate(String username, String password, String codiceAbi) {
        Optional<User> user = userRepository.findByUsernameAndCodiceAbi(username, codiceAbi);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(user.get());
        }
        throw new RuntimeException("Credenziali non valide");
    }

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private String generateToken(User user) {
        byte[] key = Base64.getDecoder().decode(jwtSecret);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("codiceAbi", user.getCodiceAbi())
                .claim("ruolo", user.getRuolo())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
