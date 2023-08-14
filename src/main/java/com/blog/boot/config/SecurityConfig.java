package com.blog.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Marks this class as a Spring configuration with bean definitions and settings.
@Configuration
public class SecurityConfig {

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
                                .anyRequest()
                                .authenticated())

                // Configure the application to use HTTP Basic Authentication.
                .httpBasic(Customizer.withDefaults());

        // Return the configured security filter chain implementation class
        // for the SecurityFilterChain interface.
        return httpSecurity.build();
    }
}
