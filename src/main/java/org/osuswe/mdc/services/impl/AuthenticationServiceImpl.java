package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.JwtAuthenticationResponse;
import org.osuswe.mdc.dto.SigninRequest;
import org.osuswe.mdc.dto.SignupRequest;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.AuthenticationService;
import org.osuswe.mdc.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignupRequest request) {
        var user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).build();
        userMapper.addUser(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userMapper.getUser(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid usernmae or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
