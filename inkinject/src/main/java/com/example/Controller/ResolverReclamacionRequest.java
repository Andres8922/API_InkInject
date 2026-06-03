package com.example.Controller;

import com.example.Enums.EstadoCompra;
import lombok.Data;

@Data
public class ResolverReclamacionRequest {
    private String comentarioAdmin;
    private EstadoCompra nuevoEstado;
}
