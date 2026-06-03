package com.example.Controller;

//import com.example.Entity.Cliente;
import com.example.Entity.Reclamacion;
import com.example.Enums.EstadoCompra;
import com.example.Service.ReclamacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping
//@Tag(name = "Reclamaciones", description = "Gestión de reclamaciones sobre compras")
//public class ReclamacionController {

//    @Autowired
//    private ReclamacionService reclamacionService;

//    @Autowired
//    private JWTUtils jwtUtils;
//
//    // POST /compras/{id}/reclamar — solo CLIENTE propietario
//    @PostMapping("/compras/{id}/reclamar")
//    @PreAuthorize("hasAuthority('CLIENTE')")
//    @Operation(summary = "Crear una reclamación",
//               description = "Transición de estado: PENDIENTE → RECLAMADO")
//    @ApiResponses({
//        @ApiResponse(responseCode = "201", description = "Reclamación creada correctamente"),
//        @ApiResponse(responseCode = "400", description = "Estado incorrecto"),
//        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
//        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
//    })
//    public ResponseEntity<Reclamacion> crearReclamacion(@PathVariable Long id) {
//        Cliente cliente = (Cliente) jwtUtils.userLogin();
//        Reclamacion reclamacion = reclamacionService.crearReclamacion(id, cliente.getId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(reclamacion);
//    }

    // PUT /reclamaciones/{id}/resolver — solo ADMINISTRADOR
//    @PutMapping("/reclamaciones/{id}/resolver")
//    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
//    @Operation(summary = "Resolver una reclamación",
//               description = "Transición de estado: RECLAMADO → TERMINADO o CANCELADO")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "Reclamación resuelta correctamente"),
//        @ApiResponse(responseCode = "400", description = "Estado incorrecto o comentario vacío"),
//        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
//        @ApiResponse(responseCode = "404", description = "Reclamación no encontrada")
//    })
//    public ResponseEntity<Reclamacion> resolverReclamacion(
//            @PathVariable Long id,
//            @RequestBody ResolverReclamacionRequest request) {
//        Reclamacion reclamacion = reclamacionService.resolverReclamacion(
//            id,
//            request.getComentarioAdmin(),
//            request.getNuevoEstado()
//        );
//        return ResponseEntity.ok(reclamacion);
//    }
//}
