package com.example.Repository;

import com.example.Entity.Disenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisenioRepository extends JpaRepository<Disenio, Long> {

    List<Disenio> findByEtiquetasNombre(String nombre);

    List<Disenio> findByNombreContainingIgnoreCase(String nombre);
}