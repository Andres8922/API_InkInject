package com.example.Repository;

import com.example.Entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    Optional<Valoracion> findByCompraId(Long compraId);

    List<Valoracion> findByCompraClienteId(Long clienteId);

    boolean existsByCompraId(Long compraId);

    // Se activará cuando Disenio tenga la relación con Tatuador:
    // List<Valoracion> findByCompraDisenioTatuadorId(Long tatuadorId);
}