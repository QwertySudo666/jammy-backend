package com.jammy.controllers;

import com.jammy.dto.AccessTokenResponse;
import com.jammy.dto.Tokens;
import com.jammy.dto.LoginUserRequest;
import com.jammy.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginUserRequest request, HttpServletResponse response) {
        Tokens tokens = tokenService.login(request);

        setRefreshTokenInCookie(response, tokens.getRefreshToken());

        return ResponseEntity.ok()
                .body(new AccessTokenResponse(tokens.getAccessToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()) && cookie.getAttributes() != null)
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token missing"));

        var newTokens = tokenService.refreshToken(refreshToken);

        setRefreshTokenInCookie(response, newTokens.getRefreshToken());

        return ResponseEntity.ok(newTokens.getAccessToken());
    }

    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/v1/auth/refresh-token")
                .maxAge(Duration.ofDays(1))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}

