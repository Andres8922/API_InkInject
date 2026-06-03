package com.example.Controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Datos editables del perfil del actor")
public class ActorPerfilRequest {

    @NotBlank
    @Schema(description = "Nombre del actor", example = "Juan")
    private String nombre;

    @NotBlank
    @Schema(description = "Apellidos del actor", example = "Garcia Lopez")
    private String apellidos;

    @NotBlank
    @Email
    @Schema(description = "Email del actor", example = "juan@email.com")
    private String email;

    @Pattern(regexp = "^[6-9][0-9]{8}$")
    @Schema(description = "Telefono del actor", example = "612345678")
    private String telefono;

    @NotBlank
    @Schema(description = "Direccion del actor", example = "Calle Mayor 1")
    private String direccion;

    @NotBlank
    @Schema(description = "Ciudad del actor", example = "Sevilla")
    private String ciudad;
}