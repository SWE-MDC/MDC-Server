package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.GetRoleResponse;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.services.JwtService;
import org.osuswe.mdc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resource/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping("/role")
    public ResponseEntity<GetRoleResponse> getUserRole(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        String username = jwtService.extractUserName(token);
        UserDetails userDetails = userService.userDetailsService()
                .loadUserByUsername(username);
        GetRoleResponse resp;
        if (jwtService.isTokenValid(token, userDetails)) {
            Role role = userService.getRoleByUsername(userDetails.getUsername());
            resp = new GetRoleResponse(HttpStatus.OK.value(), "");
            resp.setData(role);
        } else {
            resp = new GetRoleResponse(HttpStatus.BAD_REQUEST.value(), "Invalid token");
        }
        return ResponseEntity.ok(resp);
    }
}
