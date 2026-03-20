package com.educommerce.studentservice.controller;

import com.educommerce.studentservice.dto.AuthResponseDTO;
import com.educommerce.studentservice.dto.LoginRequestDTO;
import com.educommerce.studentservice.dto.RegisterRequestDTO;
import com.educommerce.studentservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        AuthResponseDTO response = authService.register(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        AuthResponseDTO response = authService.login(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
