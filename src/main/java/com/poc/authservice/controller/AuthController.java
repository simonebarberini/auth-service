package com.poc.authservice.controller;

import com.poc.authservice.model.User;
import com.poc.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
