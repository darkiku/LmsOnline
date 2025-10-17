package com.example.LmsProject.Controller;

import com.example.LmsProject.Dto.LoginResponse;
import com.example.LmsProject.Dto.LoginUserDto;
import com.example.LmsProject.Dto.RegisterUserDto;
import com.example.LmsProject.Dto.VerifyUserDto;
import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Service.AuthenticationService;
import com.example.LmsProject.Service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        Student registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok("Registration successful. Please verify your email.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        Student loginedUser = authenticationService.signIn(loginUserDto);
        String token = jwtService.generateToken(loginedUser);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyUserDto verifyUserDto) {
        authenticationService.verifyUser(verifyUserDto);
        return ResponseEntity.ok("Email verified successfully.");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resend(@RequestParam String email) {
        authenticationService.resendVerificationEmail(email);
        return ResponseEntity.ok("Verification code has been sent");
    }
}