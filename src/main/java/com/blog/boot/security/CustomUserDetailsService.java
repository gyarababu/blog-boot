package com.blog.boot.security;

import com.blog.boot.entity.User;
import com.blog.boot.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {

        // find the user from database
        // by userName or email or return exception and assign to entity
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "
                                + userNameOrEmail));

        // granting roles to users
        Set<GrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                // converting one object to another object
                .map((role) -> new SimpleGrantedAuthority(
                        // passing the role entity parameter
                        role.getName())).collect(Collectors.toSet());

        // created an instance of user class using provided user by spring security
        // and loading from a database
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}
