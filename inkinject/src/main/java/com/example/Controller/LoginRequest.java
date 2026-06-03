package com.example.Controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Credenciales de acceso")
public class LoginRequest {

    @Schema(description = "Nombre de usuario", example = "usuario")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "password")
    private String password;
}