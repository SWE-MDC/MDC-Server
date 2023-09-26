package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.osuswe.mdc.dto.*;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.services.AuthenticationService;
import org.osuswe.mdc.util.EmailUtil;
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
        if (!EmailUtil.validateEmail(request.getEmail())) {
            throw new InvalidArgumentException("Invalid email: " + request.getEmail());
        }

        if (request.getPassword().length() < 10) {
            throw new InvalidArgumentException("Password is too short");
        }

        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @RequestMapping(value = "/activate/{email}", method = RequestMethod.GET)
    public ResponseEntity<ActivateResponse> signup(@PathVariable("email") String email) {
        ActivateRequest request = new ActivateRequest();
        request.setEmail(email);
        return ResponseEntity.ok(authenticationService.activate(request));
    }

//    @GetMapping("/test")
//    public ResponseEntity<JwtAuthenticationResponse> test() {
//        return ResponseEntity.ok(new JwtAuthenticationResponse());
//    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
