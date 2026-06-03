package com.example.Service;

import com.example.Entity.Disenio;
import com.example.Entity.Etiqueta;
import com.example.Repository.DisenioRepository;
import com.example.Repository.EtiquetaRepository;
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
        // Cuando exista la entidad Tatuador se validará aquí que
        // el usuario autenticado es el tatuador que sube el diseño
        validarTatuadorAutenticado(disenio, usernameTatuador);

        disenio.setFechaSubida(LocalDate.now());

        // Resolver etiquetas desde BBDD
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
                .orElseThrow(() -> new RuntimeException("Diseño no encontrado con id: " + id));

        validarTatuadorAutenticado(disenio, usernameTatuador);

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
                .orElseThrow(() -> new RuntimeException("Diseño no encontrado con id: " + id));

        validarTatuadorAutenticado(disenio, usernameTatuador);

        disenioRepository.delete(disenio);
    }

    // Cuando exista Tatuador se completará esta validación comprobando
    // que disenio.getTatuador().getUsername().equals(usernameTatuador)
    private void validarTatuadorAutenticado(Disenio disenio, String usernameTatuador) {
        if (usernameTatuador == null || usernameTatuador.isBlank()) {
            throw new RuntimeException("No hay ningún tatuador autenticado");
        }
    }
}