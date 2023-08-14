package com.blog.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Marks this class as a Spring configuration with bean definitions and settings.
@Configuration
// enabling method level security to SecurityConfig for using annotations like
// @PreAuthorize or @PostAuthorize for every endpoint
@EnableMethodSecurity
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
                                // all the USER roles can access get REST APIs
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                // apart from GET endpoint needs to be authenticated
                                .anyRequest().authenticated()

                // Configure the application to use HTTP Basic Authentication.
                ).httpBasic(Customizer.withDefaults());

        // Return the configured security filter chain implementation class
        // for the SecurityFilterChain interface.
        return httpSecurity.build();
    }

    @Bean
    // using inbuilt UserDetailsService for giving credentials and roles
    public UserDetailsService userDetailsService(){
        // using UserDetails for mentioning single person details
        UserDetails john = User.builder()
                .username("john")
                // encoding password
                .password(passwordEncoder().encode("john"))
                .roles("USER")
                .build();

        // creating one more person details
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        // now details are in InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(john, admin);
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        // encoding and returning BCryptPasswordEncoder class by using PasswordEncoder interface
        return new BCryptPasswordEncoder();
    }
}
