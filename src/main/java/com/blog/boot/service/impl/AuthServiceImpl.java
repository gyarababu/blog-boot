package com.blog.boot.service.impl;

import com.blog.boot.controller.PostController;
import com.blog.boot.entity.Role;
import com.blog.boot.entity.User;
import com.blog.boot.exception.BlogAPIException;
import com.blog.boot.payload.LoginDto;
import com.blog.boot.payload.RegisterDto;
import com.blog.boot.repository.RoleRepository;
import com.blog.boot.repository.UserRepository;
import com.blog.boot.security.JwtTokenProvider;
import com.blog.boot.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // creating login method and authenticating using in built Authentication interface
    @Override
    public String login(LoginDto loginDto) {
        logger.info("started login or signin service class for user info log level ");

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
        logger.info("ended login or signin service class for user info log level ");
        // generate JWT token and return it
        String token = jwtTokenProvider.generateJwtToken(authentication);
        logger.info("generated JWT Token in service class for user info log level ");
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        logger.info("started register or signup service class for user info log level ");

        // checking userName already exists
        if (userRepository.existsByUserName(registerDto.getUserName())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // checking email already exists
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // create an object of User entity
        User user = new User();
        // passing all the details from RegisterDto to User
        user.setName(registerDto.getName());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Create a set to hold user roles
        Set<Role> roles = new HashSet<>();
        // Convert UserRole enum to role name and fetch role from repository
        Role userRole = roleRepository.findByName(registerDto.getUserRole().name());
        roles.add(userRole); // Add the user role to the roles set
        user.setRoles(roles); // Assign the roles set to the user's roles

        // Save the user object in the userRepository
        userRepository.save(user);
        logger.info("ended register or signup service class for user info log level ");

        // Return a success message after user registration
        return "User registered successfully";
    }
}
