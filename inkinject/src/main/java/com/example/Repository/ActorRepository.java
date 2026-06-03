package com.example.Repository;

import com.example.Entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByUsername(String username);

    Optional<Actor> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}