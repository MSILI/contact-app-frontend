package com.app.contacts.services.impl;

import com.app.contacts.entities.User;
import com.app.contacts.exceptions.NotFoundException;
import com.app.contacts.exceptions.SignInException;
import com.app.contacts.repositories.UserRepository;
import com.app.contacts.security.JwtTokenProvider;
import com.app.contacts.security.LoginRequest;
import com.app.contacts.security.LoginResponse;
import com.app.contacts.services.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AccountServiceImpl(UserRepository userRepository,
                              AuthenticationManager authenticationManager,
                              JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        if (token == null) {
            throw new SignInException("Erreur d'authentifciation, Merci de vérifier votre login et mot de passe");
        }
        Optional<User> userOptional = this.userRepository.findByUsername(loginRequest.getUsername());
        return userOptional.map(user -> {
            return LoginResponse.builder()
                    .tokenType("Bearer ")
                    .token(token)
                    .authenticatedUser(user)
                    .build();
        }).orElseThrow(() -> new NotFoundException("L'utilisateur avec username: "
                + loginRequest.getUsername() + " est introuvable!"));
    }
}
