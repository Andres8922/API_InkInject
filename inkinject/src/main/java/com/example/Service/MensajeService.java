package com.example.Service;

import com.example.Entity.Mensaje;
import com.example.Repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public List<Mensaje> findAll() {
        return mensajeRepository.findAll();
    }

    public Optional<Mensaje> findById(Long id) {
        return mensajeRepository.findById(id);
    }

    public Mensaje send(Mensaje mensaje, String usernameEmisor) {
        if (usernameEmisor == null || usernameEmisor.isBlank()) {
            throw new RuntimeException("No hay ningún usuario autenticado");
        }

        // Cuando exista Actor se validará aquí que el emisor es el usuario autenticado
        // y que el receptor existe en el sistema

        mensaje.setFechaHora(LocalDateTime.now());
        return mensajeRepository.save(mensaje);
    }

    public void delete(Long id, String usernameActor) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado con id: " + id));

        // Cuando exista Actor se validará aquí que solo el emisor
        // o el receptor pueden eliminar el mensaje

        mensajeRepository.delete(mensaje);
    }

    // Cuando exista Actor se activarán estos métodos:

  public List<Mensaje> findByEmisor(Long emisorId) {
      return mensajeRepository.findByEmisorId(emisorId);
  }

  public List<Mensaje> findByReceptor(Long receptorId) {
      return mensajeRepository.findByReceptorId(receptorId);
  }

  public List<Mensaje> findMisEmisorReceptor(Long actorId) {
      return mensajeRepository.findByEmisorIdOrReceptorId(actorId, actorId);
  }
}