package com.example.Repository;

import com.example.Entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    List<Valoracion> findByCompraDisenioTatuadorId(Long tatuadorId);
    boolean existsByCompraId(Long compraId);
}
