package com.example.Service;

import com.example.Entity.Compra;
//import com.example.Entity.Cliente;
//import com.example.Entity.Disenio;
import com.example.Enums.EstadoCompra;
import com.example.Repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    // CLIENTE: Crear compra nueva, estado inicial COMPRADO
//    public Compra crearCompra(Cliente cliente, Disenio disenio) {
//        Compra compra = new Compra();
//        compra.setTicket(UUID.randomUUID().toString());
//        compra.setFecha(LocalDateTime.now());
//        compra.setEstado(EstadoCompra.COMPRADO);
//        compra.setPrecio(disenio.getPrecio());
//        compra.setCliente(cliente);
//        compra.setDisenio(disenio);
//        return compraRepository.save(compra);
//    }

    // CLIENTE: COMPRADO → PAGADO
//    public Compra pagar(Long compraId, Long clienteId) {
//        Compra compra = obtenerCompraOError(compraId);
//        validarPropietario(compra, clienteId);
//        if (compra.getEstado() != EstadoCompra.COMPRADO) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "Solo se puede pagar una compra en estado COMPRADO");
//        }
//        compra.setEstado(EstadoCompra.PAGADO);
//        return compraRepository.save(compra);
//    }

    // TATUADOR: PAGADO → PENDIENTE
//    public Compra marcarPendiente(Long compraId, Long tatuadorId) {
//        Compra compra = obtenerCompraOError(compraId);
//        if (!compra.getDisenio().getTatuador().getId().equals(tatuadorId)) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                "No tienes permiso para gestionar esta compra");
//        }
//        if (compra.getEstado() != EstadoCompra.PAGADO) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "Solo se puede marcar como PENDIENTE una compra en estado PAGADO");
//        }
//        compra.setEstado(EstadoCompra.PENDIENTE);
//        return compraRepository.save(compra);
//    }

//    // CLIENTE: PENDIENTE → TERMINADO
//    public Compra terminar(Long compraId, Long clienteId) {
//        Compra compra = obtenerCompraOError(compraId);
//        validarPropietario(compra, clienteId);
//        if (compra.getEstado() != EstadoCompra.PENDIENTE) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "Solo se puede terminar una compra en estado PENDIENTE");
//        }
//        compra.setEstado(EstadoCompra.TERMINADO);
//        return compraRepository.save(compra);
//    }
//
//    // CLIENTE: PENDIENTE → RECLAMADO (lo gestiona ReclamacionService)
//    public Compra marcarReclamado(Long compraId, Long clienteId) {
//        Compra compra = obtenerCompraOError(compraId);
//        validarPropietario(compra, clienteId);
//        if (compra.getEstado() != EstadoCompra.PENDIENTE) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "Solo se puede reclamar una compra en estado PENDIENTE");
//        }
//        compra.setEstado(EstadoCompra.RECLAMADO);
//        return compraRepository.save(compra);
//    }

//    // ADMINISTRADOR: RECLAMADO → TERMINADO o CANCELADO (lo gestiona ReclamacionService)
//    public Compra resolverReclamacion(Long compraId, EstadoCompra nuevoEstado) {
//        Compra compra = obtenerCompraOError(compraId);
//        if (compra.getEstado() != EstadoCompra.RECLAMADO) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "Solo se puede resolver una compra en estado RECLAMADO");
//        }
//        if (nuevoEstado != EstadoCompra.TERMINADO && nuevoEstado != EstadoCompra.CANCELADO) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "El nuevo estado debe ser TERMINADO o CANCELADO");
//        }
//        compra.setEstado(nuevoEstado);
//        return compraRepository.save(compra);
//    }

    // CUALQUIER USUARIO: ver sus propias compras
    public List<Compra> obtenerComprasPorCliente(Long clienteId) {
        return compraRepository.findByClienteId(clienteId);
    }

    // Métodos auxiliares privados
    public Compra obtenerCompraOError(Long compraId) {
        return compraRepository.findById(compraId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Compra no encontrada"));
    }

//    private void validarPropietario(Compra compra, Long clienteId) {
//        if (!compra.getCliente().getId().equals(clienteId)) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                "No tienes permiso para gestionar esta compra");
//        }
//    }
}
