package com.example.Controller;

import com.example.Entity.Cliente;
import com.example.Entity.Tatuador;
import com.example.Enums.Roles;
import com.example.Service.ActorService;
import com.example.Service.ClienteService;
import com.example.Service.TatuadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registro")
@Tag(name = "Registro", description = "Registro de nuevos usuarios sin autenticacion")
public class RegistroController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TatuadorService tatuadorService;

    @Autowired
    private ActorService actorService;

    @PostMapping("/cliente")
    @Operation(
        summary = "Registro de cliente",
        description = "Endpoint publico. Registra un nuevo cliente en el sistema"
    )
    public ResponseEntity<?> registroCliente(@Valid @RequestBody Cliente cliente) {
        if (actorService.existsByUsername(cliente.getUsername())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El username ya está en uso");
            return ResponseEntity.badRequest().body(error);
        }

        if (actorService.existsByEmail(cliente.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El email ya está en uso");
            return ResponseEntity.badRequest().body(error);
        }

        if (!cliente.getTerminosAceptados()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Debes aceptar los terminos y condiciones");
            return ResponseEntity.badRequest().body(error);
        }

        cliente.setRol(Roles.CLIENTE);
        Cliente saved = clienteService.save(cliente);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Cliente registrado correctamente");
        response.put("username", saved.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/tatuador")
    @Operation(
        summary = "Registro de tatuador",
        description = "Endpoint publico. Registra un nuevo tatuador en el sistema"
    )
    public ResponseEntity<?> registroTatuador(@Valid @RequestBody Tatuador tatuador) {
        if (actorService.existsByUsername(tatuador.getUsername())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El username ya está en uso");
            return ResponseEntity.badRequest().body(error);
        }

        if (actorService.existsByEmail(tatuador.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El email ya está en uso");
            return ResponseEntity.badRequest().body(error);
        }

        if (!tatuador.getTerminosAceptados()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Debes aceptar los terminos y condiciones");
            return ResponseEntity.badRequest().body(error);
        }

        tatuador.setRol(Roles.TATUADOR);
        Tatuador saved = tatuadorService.save(tatuador);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Tatuador registrado correctamente");
        response.put("username", saved.getUsername());

        return ResponseEntity.ok(response);
    }
}