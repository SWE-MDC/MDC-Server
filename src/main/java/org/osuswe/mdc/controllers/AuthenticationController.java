package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.JwtAuthenticationResponse;
import org.osuswe.mdc.dto.SigninRequest;
import org.osuswe.mdc.dto.SignupRequest;
import org.osuswe.mdc.services.AuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignupRequest request) {
        System.out.println("Got");
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @GetMapping("/test")
    public ResponseEntity<JwtAuthenticationResponse> test() {
        return ResponseEntity.ok(new JwtAuthenticationResponse());
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
