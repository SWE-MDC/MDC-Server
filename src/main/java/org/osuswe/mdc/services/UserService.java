package org.osuswe.mdc.services;

import org.osuswe.mdc.dto.GetRoleResponse;
import org.osuswe.mdc.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    Role getRoleByUsername(String username);
}
