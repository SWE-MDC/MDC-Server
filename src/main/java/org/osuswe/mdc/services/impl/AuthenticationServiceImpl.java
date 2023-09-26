package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.*;
import org.osuswe.mdc.exception.StatusException;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.AuthenticationService;
import org.osuswe.mdc.services.JwtService;
import org.osuswe.mdc.services.MailService;
import org.springframework.http.HttpStatus;
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
    private final MailService mailService;

    @Override
    public JwtAuthenticationResponse signup(SignupRequest request) {
        if (userMapper.getUserByEmail(request.getEmail()).isPresent()) {
            throw new InvalidArgumentException("Email " + request.getEmail() + " is already used");
        }
        Role role = userMapper.getRoleByName("attendee").orElseThrow(() -> new StatusException("Role not found"));
        var user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role_id(role.getId()).expired(false).locked(true).build();
        if (userMapper.addUser(user) > 0) {
            System.out.println("Register successfully, user: " + request.getEmail());
            mailService.sendTextEmail(request.getEmail(), "Welcome to SWE",
                    "You've successfully registered a new account in our event manage system. Click http://hp.gengl.me:8081/api/v1/auth/activate/" + request.getEmail() + " to activate your account");
        }
        var jwt = jwtService.generateToken(user);
        var resp = new JwtAuthenticationResponse(HttpStatus.OK.value(), "Setup successful");
        resp.setToken(jwt);
        return resp;
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userMapper.getUser(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));
        var jwt = jwtService.generateToken(user);
        var resp = new JwtAuthenticationResponse(HttpStatus.OK.value(), "");
        resp.setToken(jwt);
        return resp;
    }

    @Override
    public ActivateResponse activate(ActivateRequest request) {
        User user = userMapper.getUserByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Cannot found user " + request.getEmail()));
        if (user.isLocked()) {
            user.setLocked(false);
            String msg;
            if (userMapper.updateUser(user) > 0) {
                msg = "Activate user successfully";
            } else {
                throw new StatusException("Failed to activate user");
            }
        } else {
            throw new StatusException("This user is not locked");
        }
        return new ActivateResponse(HttpStatus.OK.value(), "You have activated you account successfully");
    }
}
