package org.example.clinic.infra.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.clinic.domain.usuario.UsuarioRepository;
import org.example.clinic.infra.exceptions.TokenException;
import org.example.clinic.infra.exceptions.TokenMalFormadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
        private String[] swaggerPaths = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**"
    };
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if ("/auth/login".equals(requestURI) || "/auth/register".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
                    if (isSwaggerPath(request)) {
                filterChain.doFilter(request, response);
                return;
            }
        String token = extractToken(request);

        if (token != null) {
            try {
                String nombreUsuario = tokenService.getSubject(token);

                authenticateUser(nombreUsuario);
            } catch (TokenException e) {
                handleTokenException(response, e);
                return;
            }
        }
        else {
            // Token nulo o vacío
            handleTokenException(response, new TokenMalFormadoException("Token nulo o vacío"));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void authenticateUser(String nombreUsuario) {
        usuarioRepository.findByUsername(nombreUsuario)
                .ifPresent(usuario -> {
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                            usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
    }

    private void handleTokenException(HttpServletResponse response, TokenException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", e.getMessage()));
    }
        private boolean isSwaggerPath(HttpServletRequest request) {
        for (String path : swaggerPaths) {
            if (pathMatcher.match(path, request.getServletPath())) {
                return true;
            }
        }
        return false;
    }
}