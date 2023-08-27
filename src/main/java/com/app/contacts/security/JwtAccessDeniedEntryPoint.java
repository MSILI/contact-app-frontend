package com.app.contacts.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedEntryPoint implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        log.error("Réponse avec l'erreur Access Denied. Message - {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Vous n'êtes pas autorisé à accéder à cette ressource !");
    }
}