package com.example.Service;

import com.example.Entity.Compra;
import com.example.Entity.Cliente;
import com.example.Entity.Valoracion;
import com.example.Enums.EstadoCompra;
import com.example.Repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private CompraService compraService;

    // CLIENTE: Valorar una compra en estado TERMINADO
    public Valoracion crearValoracion(Long compraId, Long clienteId, 
                                      Integer puntuacion, String comentario) {
        Compra compra = compraService.obtenerCompraOError(compraId);

        // Validar que la compra pertenece al cliente
        if (!compra.getCliente().getId().equals(clienteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para valorar esta compra");
        }

        // Validar que la compra está en estado TERMINADO
        if (compra.getEstado() != EstadoCompra.TERMINADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Solo se puede valorar una compra en estado TERMINADO");
        }

        // Validar que la compra no ha sido valorada ya
        if (valoracionRepository.existsByCompraId(compraId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Esta compra ya ha sido valorada");
        }

        Valoracion valoracion = new Valoracion();
        valoracion.setPuntuacion(puntuacion);
        valoracion.setComentario(comentario);
        valoracion.setCompra(compra);
        valoracion.setCliente((Cliente) compra.getCliente());

        Valoracion saved = valoracionRepository.save(valoracion);

        // Se activará cuando Disenio tenga la relación con Tatuador:
        // actualizarMediaTatuador(compra.getDisenio().getTatuador().getId());

        return saved;
    }

    // Obtener todas las valoraciones de un tatuador
    public List<Valoracion> obtenerValoracionesPorTatuador(Long tatuadorId) {
        // Se activará cuando Disenio tenga la relación con Tatuador:
        // return valoracionRepository.findByCompraDisenioTatuadorId(tatuadorId);
        return valoracionRepository.findAll();
    }

    // Calcular y actualizar la media de valoraciones del tatuador
    private void actualizarMediaTatuador(Long tatuadorId) {
        // Se activará cuando Disenio tenga la relación con Tatuador:
        List<Valoracion> valoraciones =
            // valoracionRepository.findByCompraDisenioTatuadorId(tatuadorId);
            valoracionRepository.findAll();

        if (!valoraciones.isEmpty()) {
            double media = valoraciones.stream()
                .mapToInt(Valoracion::getPuntuacion)
                .average()
                .orElse(0.0);

            // Redondear a 2 decimales
            media = Math.round(media * 100.0) / 100.0;

            // Actualizar el campo mediaValoracion del tatuador
            // Este campo lo debe tener la entidad Tatuador de tu compañero
            // Lo llamamos aquí pero la actualización la hace TatuadorService
            // Por ahora guardamos la media calculada en memoria
            // cuando tengamos TatuadorService lo conectamos
            System.out.println("Media actualizada para tatuador "
                + tatuadorId + ": " + media);
        }
    }
}