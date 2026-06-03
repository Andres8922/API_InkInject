package com.example.Controller;

import com.example.Entity.Actor;
import com.example.Entity.Mensaje;
import com.example.Security.JWTUtils;
import com.example.Service.MensajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Operation(summary = "Listar mensajes recibidos")
    public ResponseEntity<List<Mensaje>> recibidos() {
        Actor actor = jwtUtils.userLogin();
        return ResponseEntity.ok(mensajeService.findByReceptor(actor.getId()));
    }

    @GetMapping("/enviados")
    @Operation(summary = "Listar mensajes enviados")
    public ResponseEntity<List<Mensaje>> enviados() {
        Actor actor = jwtUtils.userLogin();
        return ResponseEntity.ok(mensajeService.findByEmisor(actor.getId()));
    }

    @PostMapping
    @Operation(summary = "Enviar un mensaje")
    public ResponseEntity<Mensaje> send(@RequestBody Map<String, Object> body) {
        Actor actor = jwtUtils.userLogin();
        String asunto = (String) body.get("asunto");
        String cuerpo = (String) body.get("cuerpo");
        Long receptorId = Long.valueOf(body.get("receptorId").toString());
        return ResponseEntity.ok(mensajeService.send(asunto, cuerpo, receptorId, actor.getUsername()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un mensaje")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Actor actor = jwtUtils.userLogin();
        mensajeService.delete(id, actor.getUsername());
        return ResponseEntity.noContent().build();
    }
}