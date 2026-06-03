package com.example.Service;

import com.example.Entity.Compra;
import com.example.Entity.Reclamacion;
import com.example.Enums.EstadoCompra;
import com.example.Repository.ReclamacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReclamacionService {

    @Autowired
    private ReclamacionRepository reclamacionRepository;

    @Autowired
    private CompraService compraService;

//    // CLIENTE: Crear reclamación sobre una compra en estado PENDIENTE
//    public Reclamacion crearReclamacion(Long compraId, Long clienteId) {
//        // Cambia el estado de la compra a RECLAMADO
//        Compra compra = compraService.marcarReclamado(compraId, clienteId);
//
//        Reclamacion reclamacion = new Reclamacion();
//        reclamacion.setCompra(compra);
//        return reclamacionRepository.save(reclamacion);
//    }
//
//    // ADMINISTRADOR: Resolver reclamación añadiendo comentario y cambiando estado
//    public Reclamacion resolverReclamacion(Long reclamacionId, 
//                                           String comentarioAdmin, 
//                                           EstadoCompra nuevoEstado) {
//        Reclamacion reclamacion = reclamacionRepository.findById(reclamacionId)
//            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                "Reclamación no encontrada"));
//
//        if (comentarioAdmin == null || comentarioAdmin.isBlank()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "El administrador debe añadir un comentario al resolver la reclamación");
//        }
//
//        // Cambia el estado de la compra a TERMINADO o CANCELADO
//        compraService.resolverReclamacion(reclamacion.getCompra().getId(), nuevoEstado);
//
//        reclamacion.setComentarioAdmin(comentarioAdmin);
//        return reclamacionRepository.save(reclamacion);
//    }

    // Obtener reclamación por id de compra
    public Reclamacion obtenerPorCompra(Long compraId) {
        return reclamacionRepository.findByCompraId(compraId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No existe reclamación para esta compra"));
    }
}
