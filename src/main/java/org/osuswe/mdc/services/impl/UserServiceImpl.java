package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.GetRoleResponse;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userMapper.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Role getRoleByUsername(String username) {
        return userMapper.getRoleByUsername(username).orElseThrow(() -> new RuntimeException("Cannot find user " + username));
    }
}
