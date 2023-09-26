package org.osuswe.mdc.controllers;

import com.sun.mail.imap.Rights;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.JwtService;
import org.osuswe.mdc.services.UserService;
import org.osuswe.mdc.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyAuthenticationManager implements AuthenticationManager {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        var user = userService.userDetailsService().loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        if (!user.isAccountNonLocked()) {
            throw new DisabledException("Account is locked");
        }
        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }
}
