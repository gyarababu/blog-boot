package com.blog.boot.service;

import com.blog.boot.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
