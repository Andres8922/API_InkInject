package com.example.Controller;

import com.example.Entity.Actor;
import com.example.Entity.Administrador;
import com.example.Entity.Cliente;
import com.example.Entity.Tatuador;
import com.example.Security.JWTUtils;
import com.example.Service.ActorService;
import com.example.Service.AdministradorService;
import com.example.Service.ClienteService;
import com.example.Service.TatuadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actor")
@Tag(name = "Actor - GDPR", description = "Eliminacion de cuenta y exportacion de datos personales (GDPR)")
@SecurityRequirement(name = "bearerAuth")
public class ActorGDPRController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private ActorService actorService;

    @Autowired
    private TatuadorService tatuadorService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/exportar")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Exportar datos personales",
        description = "Devuelve un JSON con todos los datos personales del actor autenticado (GDPR)"
    )
    public ResponseEntity<?> exportarDatos() {
        Actor actor = jwtUtils.userLogin();

        if (actor == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se ha podido obtener el usuario autenticado");
            return ResponseEntity.status(401).body(error);
        }

        Map<String, Object> datos = buildDatosPersonales(actor);

        String json = mapToJson(datos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"datos_" + actor.getUsername() + ".json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @DeleteMapping("/perfil")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Eliminar cuenta propia",
        description = "Elimina la cuenta del actor autenticado y devuelve sus datos personales (GDPR)"
    )
    public ResponseEntity<?> eliminarCuenta() {
        Actor actor = jwtUtils.userLogin();

        if (actor == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se ha podido obtener el usuario autenticado");
            return ResponseEntity.status(401).body(error);
        }

        Map<String, Object> datos = buildDatosPersonales(actor);
        String json = mapToJson(datos);

        switch (actor.getRol()) {
            case TATUADOR:
                tatuadorService.delete((Tatuador) actor);
                break;
            case CLIENTE:
                clienteService.delete((Cliente) actor);
                break;
            case ADMINISTRADOR:
                administradorService.delete((Administrador) actor);
                break;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"datos_eliminados_" + actor.getUsername() + ".json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    private Map<String, Object> buildDatosPersonales(Actor actor) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("id", actor.getId());
        datos.put("username", actor.getUsername());
        datos.put("nombre", actor.getNombre());
        datos.put("apellidos", actor.getApellidos());
        datos.put("email", actor.getEmail());
        datos.put("telefono", actor.getTelefono());
        datos.put("direccion", actor.getDireccion());
        datos.put("ciudad", actor.getCiudad());
        datos.put("rol", actor.getRol().toString());
        datos.put("terminosAceptados", actor.getTerminosAceptados());
        datos.put("baneado", actor.getBaneado());
        return datos;
    }

    private String mapToJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        map.forEach((k, v) -> {
            sb.append("  \"").append(k).append("\": ");
            if (v instanceof String) {
                sb.append("\"").append(v).append("\"");
            } else {
                sb.append(v);
            }
            sb.append(",\n");
        });
        if (sb.lastIndexOf(",") != -1) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("}");
        return sb.toString();
    }
}