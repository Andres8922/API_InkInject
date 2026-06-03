package com.example.Controller;

import com.example.Entity.Etiqueta;
import com.example.Service.EtiquetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etiquetas")
@Tag(name = "Etiquetas", description = "Gestión de etiquetas de estilos de tatuaje")
public class EtiquetaController {

    @Autowired
    private EtiquetaService etiquetaService;

    @GetMapping
    @Operation(summary = "Listar todas las etiquetas", description = "Endpoint público, no requiere autenticación")
    public ResponseEntity<List<Etiqueta>> findAll() {
        return ResponseEntity.ok(etiquetaService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear una etiqueta", description = "Solo accesible por ADMINISTRADOR")
    public ResponseEntity<Etiqueta> create(@Valid @RequestBody Etiqueta etiqueta) {
        return ResponseEntity.ok(etiquetaService.save(etiqueta));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Modificar una etiqueta", description = "Solo ADMINISTRADOR. No permitido si la etiqueta está asociada a algún diseño")
    public ResponseEntity<Etiqueta> update(@PathVariable Long id, @Valid @RequestBody Etiqueta etiqueta) {
        return ResponseEntity.ok(etiquetaService.update(id, etiqueta));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar una etiqueta", description = "Solo ADMINISTRADOR. No permitido si la etiqueta está asociada a algún diseño")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etiquetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}