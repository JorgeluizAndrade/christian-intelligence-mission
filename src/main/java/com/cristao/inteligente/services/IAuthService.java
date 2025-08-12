package com.cristao.inteligente.services;

import com.cristao.inteligente.dto.input.LoginRequest;
import com.cristao.inteligente.dto.input.LoginResponse;

public interface IAuthService {
    public LoginResponse login(LoginRequest req);

}
