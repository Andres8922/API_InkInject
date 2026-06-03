package com.example.Service;

import com.example.Entity.Actor;
import com.example.Repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ActorService implements UserDetailsService {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Actor> actorO = actorRepository.findByUsername(username);

        if (actorO.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        Actor actor = actorO.get();

        if (actor.getBaneado()) {
            throw new UsernameNotFoundException("Usuario baneado: " + username);
        }

        if (!actor.getTerminosAceptados()) {
            throw new UsernameNotFoundException("El usuario no ha aceptado los términos y condiciones: " + username);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(actor.getRol().toString()));

        return new User(actor.getUsername(), actor.getPassword(), authorities);
    }

    public Optional<Actor> findByUsername(String username) {
        return actorRepository.findByUsername(username);
    }

    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public void delete(Actor actor) {
        actorRepository.delete(actor);
    }

    public boolean existsByUsername(String username) {
        return actorRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return actorRepository.existsByEmail(email);
    }
}