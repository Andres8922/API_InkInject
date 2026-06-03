package com.example.Service;

import com.example.Entity.Disenio;
import com.example.Entity.Etiqueta;
import com.example.Entity.Tatuador;
import com.example.Repository.DisenioRepository;
import com.example.Repository.EtiquetaRepository;
import com.example.Repository.TatuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DisenioService {

    @Autowired
    private DisenioRepository disenioRepository;

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Autowired
    private TatuadorRepository tatuadorRepository;

    public List<Disenio> findAll() {
        return disenioRepository.findAll();
    }

    public Optional<Disenio> findById(Long id) {
        return disenioRepository.findById(id);
    }

    public List<Disenio> findByEtiqueta(String nombre) {
        return disenioRepository.findByEtiquetasNombre(nombre);
    }

    public List<Disenio> findByNombre(String nombre) {
        return disenioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Disenio save(Disenio disenio, String usernameTatuador) {
        validarYAsignarTatuador(disenio, usernameTatuador);
        disenio.setFechaSubida(LocalDate.now());

        if (disenio.getEtiquetas() != null) {
            Set<Etiqueta> etiquetasResueltas = disenio.getEtiquetas().stream()
                    .map(e -> etiquetaRepository.findById(e.getId())
                            .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada con id: " + e.getId())))
                    .collect(Collectors.toSet());
            disenio.setEtiquetas(etiquetasResueltas);
        }

        return disenioRepository.save(disenio);
    }

    public Disenio update(Long id, Disenio disenioActualizado, String usernameTatuador) {
        Disenio disenio = disenioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disenio no encontrado con id: " + id));

        validarYAsignarTatuador(disenio, usernameTatuador);

        disenio.setNombre(disenioActualizado.getNombre());
        disenio.setDescripcion(disenioActualizado.getDescripcion());
        disenio.setImagenUrl(disenioActualizado.getImagenUrl());
        disenio.setPrecio(disenioActualizado.getPrecio());

        if (disenioActualizado.getEtiquetas() != null) {
            Set<Etiqueta> etiquetasResueltas = disenioActualizado.getEtiquetas().stream()
                    .map(e -> etiquetaRepository.findById(e.getId())
                            .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada con id: " + e.getId())))
                    .collect(Collectors.toSet());
            disenio.setEtiquetas(etiquetasResueltas);
        }

        return disenioRepository.save(disenio);
    }

    public void delete(Long id, String usernameTatuador) {
        Disenio disenio = disenioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disenio no encontrado con id: " + id));

        validarYAsignarTatuador(disenio, usernameTatuador);

        disenioRepository.delete(disenio);
    }

    private void validarYAsignarTatuador(Disenio disenio, String usernameTatuador) {
        if (usernameTatuador == null || usernameTatuador.isBlank()) {
            throw new RuntimeException("No hay ningun tatuador autenticado");
        }
        Tatuador tatuador = tatuadorRepository.findByUsername(usernameTatuador)
                .orElseThrow(() -> new RuntimeException("Tatuador no encontrado: " + usernameTatuador));
        disenio.setTatuador(tatuador);
    }
}