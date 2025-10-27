package com.example.userservice.service;

import com.example.userservice.dto.UserInfoDetails;
import com.example.userservice.entity.UserInfo;
import com.example.userservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfofDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
        return userInfo.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));
    }
}
