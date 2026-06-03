package com.example.Controller;

import com.example.Entity.Compra;
import com.example.Entity.Cliente;
import com.example.Entity.Tatuador;
import com.example.Entity.Disenio;
import com.example.Enums.EstadoCompra;
import com.example.Service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.Security.JWTUtils;

import java.util.List;

@RestController
@RequestMapping("/compras")
@Tag(name = "Compras", description = "Gestión del ciclo de vida de las compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private JWTUtils jwtUtils;

    // POST /compras — solo CLIENTE
    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    @Operation(summary = "Crear una compra",
               description = "El cliente compra un diseño de tatuaje. Estado inicial: COMPRADO")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Compra creada correctamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<Compra> crearCompra(@RequestBody CrearCompraRequest request) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        Disenio disenio = new Disenio();
        disenio.setId(request.getDisenioId());
        Compra compra = compraService.crearCompra(cliente, disenio);
        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }

    // PUT /compras/{id}/pagar — solo CLIENTE propietario
    @PutMapping("/{id}/pagar")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @Operation(summary = "Pagar una compra",
               description = "Transición de estado: COMPRADO → PAGADO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Compra pagada correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado incorrecto"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    public ResponseEntity<Compra> pagar(@PathVariable Long id) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.pagar(id, cliente.getId()));
    }

    // PUT /compras/{id}/pendiente — solo TATUADOR
    @PutMapping("/{id}/pendiente")
    @PreAuthorize("hasAuthority('TATUADOR')")
    @Operation(summary = "Marcar compra como pendiente",
               description = "Transición de estado: PAGADO → PENDIENTE")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Compra marcada como PENDIENTE"),
        @ApiResponse(responseCode = "400", description = "Estado incorrecto"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    public ResponseEntity<Compra> marcarPendiente(@PathVariable Long id) {
        Tatuador tatuador = (Tatuador) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.marcarPendiente(id, tatuador.getId()));
    }

    // PUT /compras/{id}/terminar — solo CLIENTE propietario
    @PutMapping("/{id}/terminar")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @Operation(summary = "Confirmar finalización del tatuaje",
               description = "Transición de estado: PENDIENTE → TERMINADO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Compra terminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado incorrecto"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    public ResponseEntity<Compra> terminar(@PathVariable Long id) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.terminar(id, cliente.getId()));
    }

    // GET /compras — cada usuario ve sus propias compras
    @GetMapping
    @PreAuthorize("hasAnyAuthority('CLIENTE', 'TATUADOR', 'ADMINISTRADOR')")
    @Operation(summary = "Listar compras",
               description = "Cada cliente ve sus propias compras")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de compras obtenida"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<List<Compra>> listarCompras() {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.obtenerComprasPorCliente(cliente.getId()));
    }
}
