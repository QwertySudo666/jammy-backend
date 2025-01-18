package com.jammy.services;

import com.jammy.dto.Tokens;
import com.jammy.dto.LoginUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private final Long ACCESS_TOKEN_EXP = 900L; //15m
    private final Long REFRESH_TOKEN_EXP = 3600L; //1h

    public Tokens login(LoginUserRequest request) {
        var userDetails = this.userService.loadUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String accessToken = generateToken(userDetails, Map.of("type", "access"), ACCESS_TOKEN_EXP);
        String refreshToken = generateToken(userDetails, Map.of("type", "refresh"), REFRESH_TOKEN_EXP);

        return new Tokens(accessToken, refreshToken);
    }

    public Tokens refreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        validateTokenType(jwt, "refresh");

        UserDetails userDetails = userService.loadUserByUsername(jwt.getSubject());


        String accessToken = generateToken(userDetails, Map.of("type", "access"), ACCESS_TOKEN_EXP);
        String newRefreshToken = generateToken(userDetails, Map.of("type", "refresh"), REFRESH_TOKEN_EXP);

        return new Tokens(accessToken, newRefreshToken);
    }

    public void validateTokenType(String token, String expectedType) {
        validateTokenType(jwtDecoder.decode(token), expectedType);
    }

    private String generateToken(UserDetails userDetails, Map<String, String> additionalClaims, long expiry) {
        Instant now = Instant.now();

        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
                .issuer("jammy-auth-service")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claims(claims -> {
                    claims.put("scope", userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(" ")));
                    claims.putAll(additionalClaims); // Додавання додаткових claims
                })
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaims)).getTokenValue();
    }

    private void validateTokenType(Jwt jwt, String expectedType) {
        String tokenType = jwt.getClaim("type");
        if (!expectedType.equals(tokenType)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token type");
        }
    }
}
