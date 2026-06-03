package com.example.Controller;

import com.example.Security.JWTUtils;
import com.example.Entity.Cliente;
import com.example.Entity.Valoracion;
import com.example.Service.ValoracionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "Valoraciones", description = "Gestión de valoraciones de compras")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private JWTUtils jwtUtils;

    // POST /compras/{id}/valorar — solo CLIENTE propietario
    @PostMapping("/compras/{id}/valorar")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @Operation(summary = "Valorar una compra",
               description = "El cliente valora una compra en estado TERMINADO con puntuación entre 0 y 5")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Valoración creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado incorrecto o compra ya valorada"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    public ResponseEntity<Valoracion> crearValoracion(
            @PathVariable Long id,
            @Valid @RequestBody CrearValoracionRequest request) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        Valoracion valoracion = valoracionService.crearValoracion(
            id,
            cliente.getId(),
            request.getPuntuacion(),
            request.getComentario()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(valoracion);
    }

    // GET /tatuadores/{id}/valoraciones — público
    @GetMapping("/tatuadores/{id}/valoraciones")
    @Operation(summary = "Listar valoraciones de un tatuador",
               description = "Devuelve todas las valoraciones recibidas por un tatuador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de valoraciones obtenida"),
        @ApiResponse(responseCode = "404", description = "Tatuador no encontrado")
    })
    public ResponseEntity<List<Valoracion>> listarValoracionesPorTatuador(
            @PathVariable Long id) {
        return ResponseEntity.ok(valoracionService.obtenerValoracionesPorTatuador(id));
    }
}
