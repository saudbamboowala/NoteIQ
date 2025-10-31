package com.example.NoteIQ_AuthService.Service;

import com.example.NoteIQ_AuthService.Domain.Users;
import com.example.NoteIQ_AuthService.Exception.InvalidCredentialsException;
import com.example.NoteIQ_AuthService.Exception.UserAlreadyExistsException;
import com.example.NoteIQ_AuthService.Exception.UserNotFoundException;
import com.example.NoteIQ_AuthService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(Users users) throws UserAlreadyExistsException {
        Optional<Users> user = userRepository.findByEmailId(users.getEmailId());

        if(user.isPresent()){
            throw new UserAlreadyExistsException("User already exists. Use a different email Id.");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(users);
    }

    @Override
    public Optional<Users> login(String emailId, String password) throws UserNotFoundException, InvalidCredentialsException {
        Optional<Users> user = userRepository.findByEmailId(emailId);

        if(user.isEmpty()){
            throw new InvalidCredentialsException("No user found with the provided email.");
        }

        if(!passwordEncoder.matches(password, user.get().getPassword())){
            throw new InvalidCredentialsException("Incorrect password. Please try again.");
        }

        return user;
    }
}
