package com.example.Controller;

import com.example.Entity.Actor;
import com.example.Entity.Administrador;
import com.example.Entity.Cliente;
import com.example.Entity.Tatuador;
import com.example.Security.JWTUtils;
import com.example.Service.AdministradorService;
import com.example.Service.ClienteService;
import com.example.Service.TatuadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actor")
@Tag(name = "Actor - Perfil", description = "Gestion del perfil del actor autenticado")
@SecurityRequirement(name = "bearerAuth")
public class ActorPerfilController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private TatuadorService tatuadorService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AdministradorService administradorService;

    @PutMapping("/perfil")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Editar perfil",
        description = "Permite a cualquier actor autenticado editar su propio perfil"
    )
    public ResponseEntity<?> editarPerfil(@Valid @RequestBody ActorPerfilRequest request) {
        Actor actor = jwtUtils.userLogin();

        if (actor == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se ha podido obtener el usuario autenticado");
            return ResponseEntity.status(401).body(error);
        }

        actor.setNombre(request.getNombre());
        actor.setApellidos(request.getApellidos());
        actor.setEmail(request.getEmail());
        actor.setTelefono(request.getTelefono());
        actor.setDireccion(request.getDireccion());
        actor.setCiudad(request.getCiudad());

        switch (actor.getRol()) {
            case TATUADOR:
                tatuadorService.update((Tatuador) actor);
                break;
            case CLIENTE:
                clienteService.update((Cliente) actor);
                break;
            case ADMINISTRADOR:
                administradorService.update((Administrador) actor);
                break;
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Perfil actualizado correctamente");
        response.put("username", actor.getUsername());

        return ResponseEntity.ok(response);
    }
}