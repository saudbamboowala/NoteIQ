package com.example.NoteIQ_AuthService.Repository;

import com.example.NoteIQ_AuthService.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByEmailId(String emailId);
}
