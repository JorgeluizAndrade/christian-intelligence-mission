package com.cristao.inteligente.application.service;

import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest req, HttpServletResponse response);

    LoginResponse refreshAccessToken(String refreshToken, HttpServletResponse response);

    void logout(HttpServletResponse response);
}
