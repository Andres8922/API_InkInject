package com.example.Service;

import com.example.Entity.Actor;
import com.example.Entity.Mensaje;
import com.example.Repository.ActorRepository;
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

    @Autowired
    private ActorRepository actorRepository;

    public List<Mensaje> findAll() {
        return mensajeRepository.findAll();
    }

    public Optional<Mensaje> findById(Long id) {
        return mensajeRepository.findById(id);
    }

    public Mensaje send(String asunto, String cuerpo, Long receptorId, String usernameEmisor) {
        Actor emisor = actorRepository.findByUsername(usernameEmisor)
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado: " + usernameEmisor));
        Actor receptor = actorRepository.findById(receptorId)
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado con id: " + receptorId));

        Mensaje mensaje = new Mensaje();
        mensaje.setAsunto(asunto);
        mensaje.setCuerpo(cuerpo);
        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);
        mensaje.setFechaHora(LocalDateTime.now());
        return mensajeRepository.save(mensaje);
    }

    public void delete(Long id, String usernameActor) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado con id: " + id));
        mensajeRepository.delete(mensaje);
    }

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