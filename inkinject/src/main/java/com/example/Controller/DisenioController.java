package com.example.Controller;

import com.example.Entity.Disenio;
import com.example.Service.DisenioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/disenios")
@Tag(name = "Diseños", description = "Gestión de diseños de tatuajes")
public class DisenioController {

    @Autowired
    private DisenioService disenioService;

    @GetMapping
    @Operation(summary = "Listar todos los diseños", description = "Endpoint público, no requiere autenticación")
    public ResponseEntity<List<Disenio>> findAll() {
        return ResponseEntity.ok(disenioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un diseño por ID", description = "Endpoint público, no requiere autenticación")
    public ResponseEntity<Disenio> findById(@PathVariable Long id) {
        return disenioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TATUADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Subir un diseño", description = "Solo accesible por TATUADOR autenticado")
    public ResponseEntity<Disenio> create(@Valid @RequestBody Disenio disenio, Principal principal) {
        return ResponseEntity.ok(disenioService.save(disenio, principal.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('TATUADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Modificar un diseño", description = "Solo el TATUADOR propietario del diseño puede modificarlo")
    public ResponseEntity<Disenio> update(@PathVariable Long id,
                                          @Valid @RequestBody Disenio disenio,
                                          Principal principal) {
        return ResponseEntity.ok(disenioService.update(id, disenio, principal.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TATUADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar un diseño", description = "Solo el TATUADOR propietario del diseño puede eliminarlo")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        disenioService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}