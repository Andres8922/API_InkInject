package com.example.Entity;

import com.example.Enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name = "actores")
@Schema(description = "Entidad base para todos los usuarios del sistema")
public abstract class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del actor", example = "1")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Nombre del actor", example = "Juan")
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Apellidos del actor", example = "Garcia Lopez")
    private String apellidos;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    @Schema(description = "Email del actor", example = "juan@email.com")
    private String email;

    @Pattern(regexp = "^[6-9][0-9]{8}$")
    @Column(nullable = false)
    @Schema(description = "Telefono del actor", example = "612345678")
    private String telefono;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Direccion del actor", example = "Calle Mayor 1")
    private String direccion;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Ciudad del actor", example = "Sevilla")
    private String ciudad;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre de usuario unico", example = "juangarcia")
    private String username;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Contrasena del actor (encriptada)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Rol del actor en el sistema", example = "CLIENTE")
    private Roles rol;

    @NotNull
    @Column(nullable = false)
    @Schema(description = "Indica si el actor ha aceptado los terminos y condiciones", example = "true")
    private Boolean terminosAceptados;

    @Column(nullable = false)
    @Schema(description = "Indica si el actor esta baneado", example = "false")
    private Boolean baneado = false;
}