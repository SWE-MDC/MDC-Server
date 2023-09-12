package org.osuswe.mdc.services;


import org.osuswe.mdc.dto.JwtAuthenticationResponse;
import org.osuswe.mdc.dto.SigninRequest;
import org.osuswe.mdc.dto.SignupRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
