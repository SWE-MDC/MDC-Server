package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.osuswe.mdc.dto.*;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.services.AuthenticationService;
import org.osuswe.mdc.services.UserService;
import org.osuswe.mdc.util.EmailUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignupRequest request) {
        if (!EmailUtil.validateEmail(request.getEmail())) {
            throw new InvalidArgumentException("Invalid email: " + request.getEmail());
        }
        if (StringUtils.isEmpty(request.getUsername())) {
            request.setUsername(request.getEmail().split("@")[0]);
        }

        if (request.getPassword().length() < 10) {
            throw new InvalidArgumentException("Password is too short");
        }

        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @RequestMapping(value = "/activate/{email}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> activate(@PathVariable("email") String email) {
        ActivateRequest request = new ActivateRequest();
        request.setEmail(email);
        var resp = authenticationService.activate(request);
        return ResponseEntity.ok(resp);
    }

//    @GetMapping("/test")
//    public ResponseEntity<JwtAuthenticationResponse> test() {
//        return ResponseEntity.ok(new JwtAuthenticationResponse());
//    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

//    @RequestMapping(value = "/reset/{email}", method = RequestMethod.GET)
//    public ResponseEntity<ErrorResponse> reset(@PathVariable("email") String email) {
//
//    }
}
