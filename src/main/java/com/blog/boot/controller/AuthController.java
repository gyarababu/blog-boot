package com.blog.boot.controller;

import com.blog.boot.payload.JwtAuthResponse;
import com.blog.boot.payload.LoginDto;
import com.blog.boot.payload.RegisterDto;
import com.blog.boot.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // creating login REST API
    // gave multiple urls
    // changing the login REST API for JWT response
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        // setting the token
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    // creating register REST API
    // gave multiple urls
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

}
