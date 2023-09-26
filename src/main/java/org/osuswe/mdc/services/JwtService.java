package org.osuswe.mdc.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    String generateToken(String username, String password);

    boolean isTokenValid(String token, UserDetails userDetails);
}
