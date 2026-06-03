package com.example.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "etiquetas")
@Schema(description = "Etiqueta que representa un estilo de tatuaje")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la etiqueta", example = "1")
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre del estilo de tatuaje", example = "Japonés")
    private String nombre;
}