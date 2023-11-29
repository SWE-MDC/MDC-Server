package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.GeneralResponse;
import org.osuswe.mdc.dto.GetRoleResponse;
import org.osuswe.mdc.dto.UserProfile;
import org.osuswe.mdc.model.Role;
import org.osuswe.mdc.model.User;
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

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(@RequestHeader("Authorization") String bearerToken) {
        User user = userService.getUserFromBearerToken(bearerToken);
        UserProfile resp = new UserProfile();

        resp.setId(user.getId());
        resp.setFirstName(user.getFirst_name());
        resp.setLastName(user.getLast_name());
        resp.setEmail(user.getEmail());
        resp.setUsername(user.getUsername());
        resp.setPronouns(user.getPronouns());
        resp.setYear(user.getYear());
        resp.setMajor(user.getMajor());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/profile")
    public ResponseEntity<GeneralResponse> setUserProfile(@RequestHeader("Authorization") String bearerToken,
                                                          @RequestBody UserProfile profile) {
        User user = userService.getUserFromBearerToken(bearerToken);

        user.setFirst_name(profile.getFirstName());
        user.setLast_name(profile.getLastName());
        user.setPronouns(profile.getPronouns());
        user.setYear(profile.getYear());
        user.setMajor(profile.getMajor());
        userService.updateUser(user);
        return ResponseEntity.ok(new GeneralResponse(HttpStatus.OK.value(), ""));
    }
}
