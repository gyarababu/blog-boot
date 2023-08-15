package com.blog.boot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
    // using the main method here
    public static void main(String[] args) {
        // using PasswordEncoder from the security
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("john"));
        System.out.println(passwordEncoder.encode("admin"));
    }
}
