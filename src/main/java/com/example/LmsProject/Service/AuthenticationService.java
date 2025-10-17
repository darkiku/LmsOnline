package com.example.LmsProject.Service;

import com.example.LmsProject.Dto.LoginUserDto;
import com.example.LmsProject.Dto.RegisterUserDto;
import com.example.LmsProject.Dto.VerifyUserDto;
import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Repository.StudentRepository;
import jakarta.mail.MessagingException;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Data
@Service
public class AuthenticationService {
    private final StudentRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public Student signUp(RegisterUserDto input) {
        String encodedPassword = passwordEncoder.encode(input.getPassword());
        Student user = new Student(input.getUsername(), encodedPassword, input.getEmail());
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpireAt(LocalDateTime.now().plusMinutes(30));
        user.setEnabled(false);
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        sendVerificationEmail(user);
        return userRepository.save(user);
    }
    public Student signIn(LoginUserDto input) {
        Student user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified, pls verify account");
        }
        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), input.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return user;
    }
    public void verifyUser(VerifyUserDto input) {
        Optional<Student> userOptional = userRepository.findByEmail(input.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Student user = userOptional.get();
        System.out.println("User's verification code: " + user.getVerificationCode());
        System.out.println("Input verification code: " + input.getVerificationCode());
        System.out.println("Verification code expires at: " + user.getVerificationExpireAt());
        if (user.getVerificationExpireAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification code has expired");
        }
        if (!user.getVerificationCode().equals(input.getVerificationCode())) {
            throw new RuntimeException("Invalid verification code");
        }
        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationExpireAt(null);
        userRepository.save(user);
        System.out.println("User with email " + input.getEmail() + " has been successfully verified.");
    }

    public void resendVerificationEmail(String email) {
        Optional<Student> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Student user = userOptional.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpireAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public void sendVerificationEmail(Student user) {
        String subject = "Account verification code";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to my app</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            emailService.sendVerificationEmail(user.getEmail(), subject,htmlMessage);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
    private String generateVerificationCode(){
        Random random = new Random();
        int code = random.nextInt(9999);
        return String.valueOf(code);
    }

}
