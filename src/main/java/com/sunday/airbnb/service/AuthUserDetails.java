package com.sunday.airbnb.service;

import com.sunday.airbnb.DTO.request.LoginRequest;
import com.sunday.airbnb.DTO.request.UserRequest;
import com.sunday.airbnb.DTO.response.AuthResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthUserDetails {
    AuthResponse createUserHandler(UserRequest userRequest) throws Exception;

    AuthResponse signIn(@RequestBody LoginRequest loginRequest);
}
