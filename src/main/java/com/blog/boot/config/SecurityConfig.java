package com.blog.boot.config;

import com.blog.boot.security.JwtAuthenticationEntryPoint;
import com.blog.boot.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Marks this class as a Spring configuration with bean definitions and settings.
@Configuration
// enabling method level security to SecurityConfig for using annotations like
// @PreAuthorize or @PostAuthorize for every endpoint
@EnableMethodSecurity
@SecurityScheme(
        name = "Authentication Header",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    // configuring JWT in security
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // configuring JWTFilter in security
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Spring automatically provides user details from a database to AuthenticationManager
    // and does password encode or decode for us
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //  SecurityFilterChain is an interface provided by the Spring Security framework.
        //  We're configuring security filters for HTTP requests using HttpSecurity.

        // Disabling CSRF aids API development and testing easier without requiring CSRF tokens.
        httpSecurity
                .csrf().disable()
                // .csrf().and()
                // Re-enable CSRF protection in production

                // Define authorization rules for incoming HTTP requests.
                .authorizeHttpRequests(
                        // We're using a short function to specify that any request must be authenticated.
                        (authorize) -> authorize
                                // all the USER roles can access get REST APIs
                                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                                // all the USERS will be able to access login
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                // all the USERS will be able to access swagger urls
                                .requestMatchers("/swagger-ui/**").permitAll()
                                // getting all REST API documentation
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                // apart from GET endpoint needs to be authenticated
                                .anyRequest().authenticated())
                // Configure exception handling for unauthorized requests
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Configure session management to use stateless sessions
                // (no session management)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // executing JWT filter before spring security filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Return the configured security filter chain implementation class
        // for the SecurityFilterChain interface.
        return httpSecurity.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        // encoding and returning BCryptPasswordEncoder class by using PasswordEncoder interface
        return new BCryptPasswordEncoder();
    }
}
