package com.example.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mensajes")
@Schema(description = "Mensaje enviado entre usuarios del sistema")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del mensaje", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de envio del mensaje", example = "2024-05-01T10:30:00")
    private LocalDateTime fechaHora;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Asunto del mensaje", example = "Consulta sobre diseno")
    private String asunto;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Cuerpo del mensaje", example = "Me gustaria saber mas sobre el diseno de dragon")
    private String cuerpo;

    @ManyToOne
    @JoinColumn(name = "emisor_id", nullable = false)
    @Schema(description = "Actor que envia el mensaje")
    private Actor emisor;

    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    @Schema(description = "Actor que recibe el mensaje")
    private Actor receptor;
}