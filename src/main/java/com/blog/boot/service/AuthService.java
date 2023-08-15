package com.blog.boot.service;

import com.blog.boot.payload.LoginDto;
import com.blog.boot.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
