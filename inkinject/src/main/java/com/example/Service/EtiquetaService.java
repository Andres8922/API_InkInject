package com.example.Service;

import com.example.Entity.Etiqueta;
import com.example.Repository.DisenioRepository;
import com.example.Repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Autowired
    private DisenioRepository disenioRepository;

    public List<Etiqueta> findAll() {
        return etiquetaRepository.findAll();
    }

    public Optional<Etiqueta> findById(Long id) {
        return etiquetaRepository.findById(id);
    }

    public Etiqueta save(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta update(Long id, Etiqueta etiquetaActualizada) {
        Etiqueta etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada con id: " + id));

        boolean tieneDisenios = !disenioRepository.findByEtiquetasNombre(etiqueta.getNombre()).isEmpty();
        if (tieneDisenios) {
            throw new RuntimeException("No se puede modificar la etiqueta porque está asociada a uno o más diseños");
        }

        etiqueta.setNombre(etiquetaActualizada.getNombre());
        return etiquetaRepository.save(etiqueta);
    }

    public void delete(Long id) {
        Etiqueta etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada con id: " + id));

        boolean tieneDisenios = !disenioRepository.findByEtiquetasNombre(etiqueta.getNombre()).isEmpty();
        if (tieneDisenios) {
            throw new RuntimeException("No se puede eliminar la etiqueta porque está asociada a uno o más diseños");
        }

        etiquetaRepository.delete(etiqueta);
    }
}