package org.osuswe.mdc.services;


import org.osuswe.mdc.dto.*;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    ActivateResponse activate(ActivateRequest request);
}
