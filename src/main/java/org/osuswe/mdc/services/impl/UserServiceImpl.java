package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.model.VerificationCode;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.JwtService;
import org.osuswe.mdc.services.MailService;
import org.osuswe.mdc.services.ScheduledTasks;
import org.osuswe.mdc.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final MailService mailService;
    @Value("${server.base}")
    private String serverBase;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userMapper.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Role getRoleByUsername(String username) {
        return userMapper.getRoleByUsername(username).orElseThrow(() -> new RuntimeException("Cannot find user " + username));
    }

    public void resetPassword(String username) {
        User user = userMapper.getUserByUsername(username).orElseThrow(() -> new RuntimeException("Cannot find user " + username));
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        VerificationCode code = new VerificationCode();
        code.setCode(String.format("%06d", number));
        code.setTimestamp(System.currentTimeMillis());
        ScheduledTasks.verificationCodes.put(user.getUsername(), code);


        mailService.sendTextEmail(user.getEmail(), "SWE - Reset Password",
                "Click " + serverBase + "/api/v1/auth/reset/" + user.getEmail() + "?code=" + code.getCode() + " to activate your account");
    }

    public User getUserFromBearerToken(String token) {
        token = token.substring(7);
        String username = jwtService.extractUserName(token);
        return userMapper.getUserByUsername(username).orElseThrow(() -> new RuntimeException("Cannot find user " + username));
    }

    @Override
    public Optional<User> getUser(int id) {
        return userMapper.getUserById(id);
    }
}
