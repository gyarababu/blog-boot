package com.blog.boot.service.impl;

import com.blog.boot.entity.Role;
import com.blog.boot.entity.User;
import com.blog.boot.exception.BlogAPIException;
import com.blog.boot.payload.LoginDto;
import com.blog.boot.payload.RegisterDto;
import com.blog.boot.repository.RoleRepository;
import com.blog.boot.repository.UserRepository;
import com.blog.boot.service.AuthService;
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

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public String register(RegisterDto registerDto) {

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
        // Retrieve the 'ROLE_USER' role from the role repository
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole); // Add the 'ROLE_USER' role to the roles set
        user.setRoles(roles); // Assign the roles set to the user's roles

        // Save the user object in the userRepository
        userRepository.save(user);

        // Return a success message after user registration
        return "User registered successfully";
    }
}
