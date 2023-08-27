package com.app.contacts.services;

import com.app.contacts.security.LoginRequest;
import com.app.contacts.security.LoginResponse;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest);
}
