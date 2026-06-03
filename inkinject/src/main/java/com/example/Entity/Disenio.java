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
@Schema(description = "Diseno de tatuaje subido por un tatuador")
public class Disenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del diseno", example = "1")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Nombre del diseno", example = "Dragon oriental")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descripcion del diseno", example = "Diseno detallado de dragon en estilo japones")
    private String descripcion;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "URL de la imagen del diseno", example = "https://ejemplo.com/imagen.jpg")
    private String imagenUrl;

    @Column(nullable = false)
    @Schema(description = "Fecha en la que se subio el diseno", example = "2024-05-01")
    private LocalDate fechaSubida;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    @Schema(description = "Precio del diseno en euros", example = "150.0")
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "tatuador_id", nullable = false)
    @Schema(description = "Tatuador que subio el diseno")
    private Tatuador tatuador;

    @ManyToMany
    @JoinTable(
        name = "disenio_etiqueta",
        joinColumns = @JoinColumn(name = "disenio_id"),
        inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    @Schema(description = "Etiquetas de estilos asociadas al diseno")
    private Set<Etiqueta> etiquetas;
}