package com.example.Repository;

import com.example.Entity.Tatuador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TatuadorRepository extends JpaRepository<Tatuador, Long> {

    Optional<Tatuador> findByUsername(String username);

    boolean existsByUsername(String username);
}