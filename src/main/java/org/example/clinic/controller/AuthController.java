package org.example.clinic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.clinic.domain.usuario.DTO.LoginRequest;
import org.example.clinic.domain.usuario.DTO.RegisterRequest;
import org.example.clinic.domain.usuario.Role;
import org.example.clinic.domain.usuario.Usuario;
import org.example.clinic.domain.usuario.UsuarioRepository;
import org.example.clinic.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticacion", description = "obtiene el token para el usuario asignado que da acceso al resto de endpoint")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            var usuarioAutenticado = authenticationManager.authenticate(authToken);

            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new TokenJWT(JWTtoken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

}
    private record TokenJWT(String jwTtoken) {
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (usuarioRepository.existsByUsername(registerRequest.username())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }
Usuario usuario = new Usuario();
        usuario.setUsername(registerRequest.username());
        usuario.setPassword(passwordEncoder.encode(registerRequest.password()));
        usuario.setGmail(registerRequest.email());
        usuario.setRole(Role.USER);
        usuarioRepository.save(usuario);

         return ResponseEntity.ok(usuario.getUsername() + " Registrado con exito");
    }
}
