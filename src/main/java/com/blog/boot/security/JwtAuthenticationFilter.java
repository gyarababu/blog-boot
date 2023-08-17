package com.blog.boot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// Declare this class as a Spring component
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request); // Get JWT token from request header

        // Validate token and perform authentication
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){

            // Get username from token
            String userName = jwtTokenProvider.getUserName(token);

            // Load user details from the userDetailsService using the user's username
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            // Create a new authentication token with the loaded user details
            UsernamePasswordAuthenticationToken newAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // No credentials since user is already authenticated
                            userDetails.getAuthorities() // Set the user's authorities (roles and permissions)
                    );

            // Set additional details for the authentication token, like IP address and session ID
            newAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Set the created authentication token in the security context holder
            // to mark the user as authenticated
            SecurityContextHolder.getContext().setAuthentication(newAuthenticationToken);

        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    // Method to extract the token from HTTP request header
    private String getTokenFromRequest(HttpServletRequest httpServletRequest){

        // Get token from header
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            // Extract token value
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
