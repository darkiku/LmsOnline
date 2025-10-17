package com.example.LmsProject.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    @NotBlank(message = "email shouldnt be a blank")
    @Email(message = "not valid email")
    private String email;
    @NotBlank(message = "password should be not blank")
    @Size(min = 3, message = "password cant be less than 3")
    private String password;
}
