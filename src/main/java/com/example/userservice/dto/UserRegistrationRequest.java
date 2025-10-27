package com.example.userservice.dto;

import com.example.userservice.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    @NotBlank(message = "Nombres son obligatorios")
    private String name;

    @NotBlank(message = "Apellidos son obligatorios")
    private String lastName;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    private LocalDate birthDate;

    @NotNull(message = "Género es obligatorio")
    private UserInfo.Gender gender;

    @NotBlank(message = "Teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Teléfono debe contener entre 10 y 15 dígitos")
    private String phone;

    private String studentCode;

    @NotBlank(message = "Documento de identidad es obligatorio")
    private String identityDocument;

    @NotBlank(message = "Correo electrónico es obligatorio")
    @Email(message = "Formato de correo electrónico inválido")
    private String email;

    @NotNull(message = "Rol es obligatorio")
    private UserInfo.Role role;

    @NotBlank(message = "Contraseña es obligatoria")
    private String password;

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(this.name);
        userInfo.setLastName(this.lastName);
        userInfo.setBirthDate(this.birthDate);
        userInfo.setGender(this.gender);
        userInfo.setPhone(this.phone);
        userInfo.setStudentCode(this.studentCode);
        userInfo.setIdentityDocument(this.identityDocument);
        userInfo.setEmail(this.email);
        userInfo.setRole(this.role);
        userInfo.setPassword(this.password);
        return userInfo;
    }
}
