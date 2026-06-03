package com.example.Repository;

import com.example.Entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

//  Una vez creada la entidad Actor se descomentarán estos métodos:

//  List<Mensaje> findByEmisorId(Long emisorId);
//  List<Mensaje> findByReceptorId(Long receptorId);
//  List<Mensaje> findByEmisorIdOrReceptorId(Long emisorId, Long receptorId);
}