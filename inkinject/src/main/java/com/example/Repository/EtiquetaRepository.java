package com.example.Repository;

import com.example.Entity.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {

    Optional<Etiqueta> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}