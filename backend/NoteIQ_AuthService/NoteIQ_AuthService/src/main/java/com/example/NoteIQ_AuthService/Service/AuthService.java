package com.example.NoteIQ_AuthService.Service;

import com.example.NoteIQ_AuthService.Domain.Users;
import com.example.NoteIQ_AuthService.Exception.InvalidCredentialsException;
import com.example.NoteIQ_AuthService.Exception.UserAlreadyExistsException;
import com.example.NoteIQ_AuthService.Exception.UserNotFoundException;

import java.util.Optional;

public interface AuthService {
    void register(Users users) throws UserAlreadyExistsException;
    Optional<Users> login(String emailId, String password) throws UserNotFoundException, InvalidCredentialsException;
}
