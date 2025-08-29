package com.cristao.inteligente.application.service;

import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;

public interface IAuthService {
    public LoginResponse login(LoginRequest req);

}
