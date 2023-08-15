package com.blog.boot.service.impl;

import com.blog.boot.payload.LoginDto;
import com.blog.boot.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // creating login method and authenticating using in built Authentication interface
    @Override
    public String login(LoginDto loginDto) {

        // Attempting to authenticate the user using provided credentials
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUserNameOrEmail(),
                                loginDto.getPassword()));

        // Setting the authenticated user's authentication object in the security context
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        // Returning a success message indicating successful login
        return "User logged in successfully ";
    }
}
