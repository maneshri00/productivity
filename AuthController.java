package com.poductivity_mangement.productivity.controller;

import com.poductivity_mangement.productivity.service.GoogleOAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthController {

    private final GoogleOAuthService oauthService;

    public AuthController(GoogleOAuthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/auth/google")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(oauthService.buildAuthUrl());
    }

    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam String code) throws IOException {
        oauthService.exchangeCode(code);
        return "OAuth Success";
    }
}

