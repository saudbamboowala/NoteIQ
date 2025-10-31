package com.example.NoteIQ_AuthService.Security;

import com.example.NoteIQ_AuthService.Domain.Users;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String, String> generateToken(Users users);
}
