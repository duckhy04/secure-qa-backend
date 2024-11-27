package org.example.secureqabackend.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String errorResponse = "{\n" +
                "  \"status\": 401,\n" +
                "  \"error\": \"Unauthorized\",\n" +
                "  \"message\": \"" + authException.getMessage() + "\"\n" +
                "}";

        PrintWriter writer = response.getWriter();
        writer.write(errorResponse);
        writer.flush();
    }
}
