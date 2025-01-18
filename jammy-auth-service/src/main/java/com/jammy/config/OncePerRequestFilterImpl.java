package com.jammy.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Component
public class OncePerRequestFilterImpl extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public OncePerRequestFilterImpl(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (token != null) {
            try {
                Jwt jwt = jwtDecoder.decode(token);
                String tokenType = jwt.getClaim("type");

                if (isRefreshEndpoint(request) && !"refresh".equals(tokenType)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access token cannot be used for refresh endpoint");
                }

                if (!isRefreshEndpoint(request) && !"access".equals(tokenType)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Refresh token cannot be used for other endpoints");
                }

                SecurityContextHolder.getContext().setAuthentication(createAuthentication(jwt));

            } catch (JwtException | IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isRefreshEndpoint(HttpServletRequest request) {
        return "/api/v1/auth/refresh-token".equals(request.getRequestURI());
    }

    private Authentication createAuthentication(Jwt jwt) {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwt.getClaimAsString("scope"));
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorities);
    }
}