package com.example.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reclamaciones")
public class Reclamacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comentarioAdmin;

    @OneToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;
}
