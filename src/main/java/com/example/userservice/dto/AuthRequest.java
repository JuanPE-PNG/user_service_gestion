package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Correo electrónico es obligatorio")
    @Email(message = "Formato de correo electrónico inválido")
    private String email;
    
    @NotBlank(message = "Contraseña es obligatoria")
    private String password;
}