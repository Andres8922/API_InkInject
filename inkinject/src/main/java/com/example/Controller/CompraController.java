package com.example.Controller;

import com.example.Entity.Compra;
import com.example.Entity.Cliente;
import com.example.Entity.Tatuador;
import com.example.Entity.Disenio;
import com.example.Service.CompraService;
import com.example.Security.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
@Tag(name = "Compras", description = "Gestion del ciclo de vida de las compras")
@SecurityRequirement(name = "bearerAuth")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping
    @Operation(summary = "Crear una compra", description = "El cliente compra un disenio. Estado inicial: COMPRADO")
    public ResponseEntity<Compra> crearCompra(@RequestBody CrearCompraRequest request) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        Disenio disenio = new Disenio();
        disenio.setId(request.getDisenioId());
        Compra compra = compraService.crearCompra(cliente, disenio);
        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }

    @PutMapping("/{id}/pagar")
    @Operation(summary = "Pagar una compra", description = "COMPRADO a PAGADO")
    public ResponseEntity<Compra> pagar(@PathVariable Long id) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.pagar(id, cliente.getId()));
    }

    @PutMapping("/{id}/pendiente")
    @Operation(summary = "Marcar compra como pendiente", description = "PAGADO a PENDIENTE")
    public ResponseEntity<Compra> marcarPendiente(@PathVariable Long id) {
        Tatuador tatuador = (Tatuador) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.marcarPendiente(id, tatuador.getId()));
    }

    @PutMapping("/{id}/terminar")
    @Operation(summary = "Confirmar finalizacion del tatuaje", description = "PENDIENTE a TERMINADO")
    public ResponseEntity<Compra> terminar(@PathVariable Long id) {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.terminar(id, cliente.getId()));
    }

    @GetMapping
    @Operation(summary = "Listar compras", description = "Cada cliente ve sus propias compras")
    public ResponseEntity<List<Compra>> listarCompras() {
        Cliente cliente = (Cliente) jwtUtils.userLogin();
        return ResponseEntity.ok(compraService.obtenerComprasPorCliente(cliente.getId()));
    }
}