package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.osuswe.mdc.exception.InvalidArgumentException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final MailService mailService;


    @Override
    public UserDetailsService userDetailsService() {
        return username -> userMapper.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Role getRoleByUsername(String username) {
        return userMapper.getRoleByUsername(username).orElseThrow(() -> new InvalidArgumentException("Cannot find user " + username));
    }


    @Override
    public void resetPassword(String email, String verifyCode) {
        User user = userMapper.getUserByEmail(email).orElseThrow(() -> new InvalidArgumentException("Cannot find user " + email));
        VerificationCode code = ScheduledTasks.verificationCodes.get(user.getEmail());
        if (code != null && code.getCode().equals(verifyCode)) {
            String newPassword = RandomStringUtils.randomAlphanumeric(10);
            var passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);
            userMapper.updatePassword(user.getId(), encodedPassword);
            mailService.sendTextEmail(user.getEmail(), "SWE - New Password",
                    "Your new password is: " + newPassword);
        } else{
            throw new InvalidArgumentException("Invalid verify code");
        }
    }

    public User getUserFromBearerToken(String token) {
        token = token.substring(7);
        String username = jwtService.extractUserName(token);
        return userMapper.getUserByUsername(username).orElseThrow(() -> new InvalidArgumentException("Cannot find user " + username));
    }

    @Override
    public Optional<User> getUser(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
