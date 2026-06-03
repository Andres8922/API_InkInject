package com.example.Repository;

import com.example.Entity.Reclamacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReclamacionRepository extends JpaRepository<Reclamacion, Long> {
    Optional<Reclamacion> findByCompraId(Long compraId);
}