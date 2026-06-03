package com.example.Repository;

import com.example.Entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByUsername(String username);

    boolean existsByUsername(String username);
}