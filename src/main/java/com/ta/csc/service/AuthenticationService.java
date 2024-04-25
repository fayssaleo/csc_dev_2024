package com.ta.csc.service;

import com.ta.csc.config.JwtTokenUtil;
import com.ta.csc.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(User authenticationRequest) {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        User user = userService.findByEmail(authenticationRequest.getEmail());
        return jwtTokenUtil.generateToken(user);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new IllegalArgumentException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("INVALID_CREDENTIALS", e);
        }
    }
    public Boolean validateToken(String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.validateToken(token,userDetails);
    }

    public void changePassword(User user)  {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUserWithId(user);
        user.setPassword(password);
    }
}
