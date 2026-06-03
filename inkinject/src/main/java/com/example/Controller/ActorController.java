package com.example.Controller;

import com.example.Security.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Actor", description = "Autenticacion de usuarios")
public class ActorController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    @Operation(
        summary = "Login de usuario",
        description = "Endpoint publico. Devuelve un token JWT si las credenciales son correctas"
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtUtils.generateToken(authentication);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", loginRequest.getUsername());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales incorrectas");
            return ResponseEntity.status(401).body(error);
        }
    }
}