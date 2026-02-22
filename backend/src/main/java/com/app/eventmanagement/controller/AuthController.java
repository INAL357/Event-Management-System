package com.app.eventmanagement.controller;

import com.app.eventmanagement.Repositories.UserRepository;
import com.app.eventmanagement.dto.AuthRequest;
import com.app.eventmanagement.dto.AuthResponse;
import com.app.eventmanagement.dto.SignUpRequest;
import com.app.eventmanagement.model.AuthProvider;
import com.app.eventmanagement.model.Role;
import com.app.eventmanagement.security.jwt.JwtUtil;
import com.nimbusds.oauth2.sdk.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.app.eventmanagement.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private JwtUtil jwtUtil;

@Autowired
private UserDetailsService userDetailsService;

 @Autowired
private PasswordEncoder passwordEncoder;

 @Autowired
 private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getUsername());
        System.out.println("TOKEN: " + token);
        return ResponseEntity.ok(new AuthResponse(token));
    }

 @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
     System.out.println("EMAIL: " + signUpRequest.getEmail());
    if(userRepository.findByEmail(signUpRequest.getUsername()).isPresent()){
        return ResponseEntity.badRequest().body("User Already Exists");
    }

     User user = new User();
     user.setUsername(signUpRequest.getUsername());
     user.setEmail(signUpRequest.getEmail());
     user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

     user.setRole(Role.USER);
     user.setProvider(AuthProvider.LOCAL);

    userRepository.save(user);


     return  ResponseEntity.ok("User registered successfully");
 }
}