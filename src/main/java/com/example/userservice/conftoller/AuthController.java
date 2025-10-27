package com.example.userservice.conftoller;

import com.example.userservice.dto.AuthRequest;
import com.example.userservice.dto.UserRegistrationRequest;
import com.example.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registerUser")
    public String addUser(@RequestBody @Valid UserRegistrationRequest registrationRequest){
        return authService.addUser(registrationRequest.toUserInfo());
    }
    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return authService.generateToken(authRequest.getEmail());
        }else{
            throw  new RuntimeException("Credenciales inválidas.");
        }

    }
    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);

        return "Token is valid.";
    }
}
