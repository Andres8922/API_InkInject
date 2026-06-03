package com.example.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "disenios")
@Schema(description = "Diseño de tatuaje subido por un tatuador")
public class Disenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del diseño", example = "1")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Nombre del diseño", example = "Dragón oriental")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descripción del diseño", example = "Diseño detallado de dragón en estilo japonés")
    private String descripcion;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "URL de la imagen del diseño", example = "https://ejemplo.com/imagen.jpg")
    private String imagenUrl;

    @NotNull
    @Column(nullable = false)
    @Schema(description = "Fecha en la que se subió el diseño", example = "2024-05-01")
    private LocalDate fechaSubida;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    @Schema(description = "Precio del diseño en euros", example = "150.0")
    private Double precio;

//    @ManyToOne
//    @JoinColumn(name = "tatuador_id", nullable = false)
//    @Schema(description = "Tatuador que subió el diseño")
//    private Tatuador tatuador;

    @ManyToMany
    @JoinTable(
        name = "disenio_etiqueta",
        joinColumns = @JoinColumn(name = "disenio_id"),
        inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    @Schema(description = "Etiquetas de estilos asociadas al diseño")
    private Set<Etiqueta> etiquetas;
}