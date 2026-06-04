package com.example.Travel.user.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "name cannot be Blank")
    private String name;

    @Email(message = "enter a valid email")
    @NotBlank(message = "email cannot be Blank")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6 , message = "password must be greater than 6 characters")
    private String password;

}
