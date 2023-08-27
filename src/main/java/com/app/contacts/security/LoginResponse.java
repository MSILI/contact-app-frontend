package com.app.contacts.security;

import com.app.contacts.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;
    private String tokenType;
    private User authenticatedUser;
}
