package com.example.userservice.repository;

import com.example.userservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByEmail(String email);
    
    Optional<UserInfo> findByStudentCode(String studentCode);
    
    Optional<UserInfo> findByIdentityDocument(String identityDocument);
    
    boolean existsByEmail(String email);
    
    boolean existsByStudentCode(String studentCode);
    
    boolean existsByIdentityDocument(String identityDocument);
}
