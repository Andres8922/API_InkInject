package com.example.Controller;

import com.example.Entity.Actor;
import com.example.Entity.Mensaje;
import com.example.Security.JWTUtils;
import com.example.Service.MensajeService;
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
@RequestMapping("/mensajes")
@Tag(name = "Mensajes", description = "Gestion de mensajes entre usuarios autenticados")
@SecurityRequirement(name = "bearerAuth")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/recibidos")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Listar mensajes recibidos",
        description = "Devuelve los mensajes recibidos por el usuario autenticado"
    )
    public ResponseEntity<List<Mensaje>> recibidos() {
        Actor actor = jwtUtils.userLogin();
        return ResponseEntity.ok(mensajeService.findByReceptor(actor.getId()));
    }

    @GetMapping("/enviados")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Listar mensajes enviados",
        description = "Devuelve los mensajes enviados por el usuario autenticado"
    )
    public ResponseEntity<List<Mensaje>> enviados() {
        Actor actor = jwtUtils.userLogin();
        return ResponseEntity.ok(mensajeService.findByEmisor(actor.getId()));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Enviar un mensaje",
        description = "Envia un mensaje al receptor indicado. Solo usuarios autenticados"
    )
    public ResponseEntity<Mensaje> send(@Valid @RequestBody Mensaje mensaje) {
        Actor actor = jwtUtils.userLogin();
        return ResponseEntity.ok(mensajeService.send(mensaje, actor.getUsername()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Eliminar un mensaje",
        description = "Elimina un mensaje. Solo el emisor o receptor pueden eliminarlo"
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Actor actor = jwtUtils.userLogin();
        mensajeService.delete(id, actor.getUsername());
        return ResponseEntity.noContent().build();
    }
}