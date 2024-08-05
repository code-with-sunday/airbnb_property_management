package com.sunday.airbnb.service.Impl;

import com.sunday.airbnb.DTO.request.LoginRequest;
import com.sunday.airbnb.DTO.request.UserRequest;
import com.sunday.airbnb.DTO.response.AuthResponse;
import com.sunday.airbnb.enums.ROLE;
import com.sunday.airbnb.model.User;
import com.sunday.airbnb.repository.UserRepository;
import com.sunday.airbnb.security.JwtProvider;
import com.sunday.airbnb.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserDeatilsImpl implements AuthUserDetails {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsImpl customUserDetails;



    @Override
    public AuthResponse createUserHandler(UserRequest userRequest) throws Exception {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setFullName(userRequest.getFullName());

        User isEmailExist  = userRepository.findByEmail(user.getEmail());

        if(isEmailExist != null){
            log.info("checking User with email {} if already exists", user.getEmail());
            throw new Exception("Email is already used with another account");

        }

        log.info("User is a new user {} :", user.getEmail());

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User saved successfully {} :", savedUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setTitle("Welcome " + user.getEmail());
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        return authResponse;
    }


    @Override
    public AuthResponse signIn(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? String.valueOf(ROLE.ROLE_USER) : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login success");
        authResponse.setTitle(jwt);
        authResponse.setRole(ROLE.valueOf(role));

        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password....");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
}
