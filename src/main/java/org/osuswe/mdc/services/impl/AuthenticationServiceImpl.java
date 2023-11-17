package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.controllers.MyAuthenticationManager;
import org.osuswe.mdc.dto.*;
import org.osuswe.mdc.exception.StatusException;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.model.VerificationCode;
import org.osuswe.mdc.repositories.RoleMapper;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.AuthenticationService;
import org.osuswe.mdc.services.JwtService;
import org.osuswe.mdc.services.MailService;
import org.osuswe.mdc.services.ScheduledTasks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MyAuthenticationManager authenticationManager;
    private final MailService mailService;
    @Value("${server.base}")
    private String serverBase;

    @Override
    public JwtAuthenticationResponse signup(SignupRequest request) {
        if (userMapper.getUserByEmail(request.getEmail()).isPresent()) {
            throw new InvalidArgumentException("Email " + request.getEmail() + " is already used");
        }
        Role role = roleMapper.getRoleByName("attendee").orElseThrow(() -> new StatusException("Role not found"));
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
        var user = userMapper.getUserByUsernameOrEmail(request.getUsernameOrEmail()).orElseThrow(() -> new InvalidArgumentException("Invalid login credentials."));
        var role = roleMapper.getRoleById(user.getRole_id()).orElseThrow(() -> new RuntimeException("Invalid Role Id"));
        var jwt = jwtService.generateToken(user);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
        var resp = new JwtAuthenticationResponse(HttpStatus.OK.value(), "Login successful");
        resp.setToken(jwt);
        resp.setRole(role);
        return resp;
    }

    @Override
    public GeneralResponse activate(ActivateRequest request) {
        User user = userMapper.getUserByEmail(request.getEmail()).orElseThrow(() -> new InvalidArgumentException("Cannot found user " + request.getEmail()));
        String msg;
        if (user.isLocked()) {
            user.setLocked(false);
            if (userMapper.updateUser(user) > 0) {
                msg = "You have activated you account successfully";
            } else {
                throw new StatusException("Failed to activate user");
            }
        } else {
            throw new StatusException("This user is not locked");
        }
        return new GeneralResponse(HttpStatus.OK.value(), msg);
    }


    public void sendResetPasswordEmail(String email) {
        System.out.println(email);

        User user = userMapper.getUserByEmail(email).orElseThrow(() -> new InvalidArgumentException("Cannot find user " + email));
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        VerificationCode code = new VerificationCode();
        code.setCode(String.format("%06d", number));
        code.setTimestamp(System.currentTimeMillis());
        ScheduledTasks.verificationCodes.put(user.getEmail(), code);

        mailService.sendTextEmail(user.getEmail(), "SWE - Reset Password",
                "Click " + serverBase + "/api/v1/auth/reset/" + user.getEmail() + "?code=" + code.getCode() + " to reset your password");
    }
}
