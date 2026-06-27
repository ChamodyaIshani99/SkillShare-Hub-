package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.LoginRequest;
import com.skillshare.skillshare_backend.dto.request.RegisterRequest;
import com.skillshare.skillshare_backend.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}