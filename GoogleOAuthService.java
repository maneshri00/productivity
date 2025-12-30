package com.poductivity_mangement.productivity.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.poductivity_mangement.productivity.config.GoogleOAuthConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleOAuthService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    private Credential credential;

    public String buildAuthUrl() throws IOException {

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        clientId,
                        clientSecret,
                        GoogleOAuthConfig.SCOPES
                ).setAccessType("offline").build();

        return flow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .build();
    }

    public void exchangeCode(String code) throws IOException {

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        clientId,
                        clientSecret,
                        GoogleOAuthConfig.SCOPES
                ).setAccessType("offline").build();

        TokenResponse token = flow.newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();

        this.credential = flow.createAndStoreCredential(token, "user");
    }

    public Credential getCredential() {
        return credential;
    }
}

