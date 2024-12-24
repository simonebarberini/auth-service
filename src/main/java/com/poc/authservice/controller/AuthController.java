package com.poc.authservice.controller;

import com.poc.authservice.model.User;
import com.poc.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam String codiceAbi) {
        return authService.authenticate(username, password, codiceAbi);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody User user){
        authService.saveUser(user);
        return "Utente creato";
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader, HttpResponse httpResponse){
        return authService.validateToken(authHeader);
    }
}
