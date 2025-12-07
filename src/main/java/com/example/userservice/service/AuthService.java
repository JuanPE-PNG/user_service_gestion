package com.example.userservice.service;

import com.example.userservice.entity.UserInfo;
import com.example.userservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String addUser(UserInfo userInfo){
        // Validar que el correo no exista
        if (repository.existsByEmail(userInfo.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        // Validar que el documento de identidad no exista
        if (repository.existsByIdentityDocument(userInfo.getIdentityDocument())) {
            throw new RuntimeException("El documento de identidad ya está registrado");
        }
        
        // Si es estudiante, validar que el código estudiantil no exista
        if (userInfo.getRole() == UserInfo.Role.Estudiante && userInfo.getStudentCode() != null) {
            if (repository.existsByStudentCode(userInfo.getStudentCode())) {
                throw new RuntimeException("El código estudiantil ya está registrado");
            }
        }
        
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Usuario registrado exitosamente";
    }

    public void validateToken(String token){
        jwtService.validateToken(token);

    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }


    // Admin methods
    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }

    public List<UserInfo> getUsersByRole(UserInfo.Role role) {
        return repository.findByRole(role);
    }

    public UserInfo getUserById(Integer id) {
        Optional<UserInfo> user = repository.findById(id);
        return user.orElse(null);
    }

    public String updateUser(Integer id, UserInfo userInfo) {
        Optional<UserInfo> existingUser = repository.findById(id);
        if (existingUser.isPresent()) {
            UserInfo user = existingUser.get();
            
            // Validar correo único si cambió
            if (!user.getEmail().equals(userInfo.getEmail()) && repository.existsByEmail(userInfo.getEmail())) {
                throw new RuntimeException("El correo electrónico ya está registrado");
            }
            
            // Validar documento único si cambió
            if (!user.getIdentityDocument().equals(userInfo.getIdentityDocument()) && repository.existsByIdentityDocument(userInfo.getIdentityDocument())) {
                throw new RuntimeException("El documento de identidad ya está registrado");
            }
            
            // Validar código estudiantil único si cambió
            if (userInfo.getStudentCode() != null && !userInfo.getStudentCode().equals(user.getStudentCode()) && repository.existsByStudentCode(userInfo.getStudentCode())) {
                throw new RuntimeException("El código estudiantil ya está registrado");
            }
            
            user.setName(userInfo.getName());
            user.setLastName(userInfo.getLastName());
            user.setBirthDate(userInfo.getBirthDate());
            user.setGender(userInfo.getGender());
            user.setPhone(userInfo.getPhone());
            user.setStudentCode(userInfo.getStudentCode());
            user.setIdentityDocument(userInfo.getIdentityDocument());
            user.setEmail(userInfo.getEmail());
            user.setRole(userInfo.getRole());
            
            if (userInfo.getPassword() != null && !userInfo.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            }
            
            repository.save(user);
            return "Usuario actualizado exitosamente";
        }
        return "Usuario no encontrado";
    }

    public String deleteUser(Integer id) {
        Optional<UserInfo> user = repository.findById(id);
        if (user.isPresent()) {
            repository.deleteById(id);
            return "User deleted successfully";
        }
        return "User not found";
    }
}
