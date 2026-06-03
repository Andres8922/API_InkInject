package com.example.Controller;

import com.example.Entity.Disenio;
import com.example.Service.DisenioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/disenios")
@Tag(name = "Diseños", description = "Gestion de disenios de tatuajes")
public class DisenioController {

    @Autowired
    private DisenioService disenioService;

    @GetMapping
    @Operation(summary = "Listar todos los disenios", description = "Endpoint publico")
    public ResponseEntity<List<Disenio>> findAll() {
        return ResponseEntity.ok(disenioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un disenio por ID", description = "Endpoint publico")
    public ResponseEntity<Disenio> findById(@PathVariable Long id) {
        return disenioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Subir un disenio", description = "Solo TATUADOR autenticado")
    public ResponseEntity<Disenio> create(@Valid @RequestBody Disenio disenio, Principal principal) {
        return ResponseEntity.ok(disenioService.save(disenio, principal.getName()));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Modificar un disenio", description = "Solo el TATUADOR propietario")
    public ResponseEntity<Disenio> update(@PathVariable Long id,
                                          @Valid @RequestBody Disenio disenio,
                                          Principal principal) {
        return ResponseEntity.ok(disenioService.update(id, disenio, principal.getName()));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar un disenio", description = "Solo el TATUADOR propietario")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        disenioService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}