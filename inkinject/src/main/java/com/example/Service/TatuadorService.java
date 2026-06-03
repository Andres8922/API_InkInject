package com.example.Service;

import com.example.Entity.Tatuador;
import com.example.Repository.TatuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TatuadorService {

    @Autowired
    private TatuadorRepository tatuadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Tatuador> findAll() {
        return tatuadorRepository.findAll();
    }

    public Optional<Tatuador> findById(Long id) {
        return tatuadorRepository.findById(id);
    }

    public Optional<Tatuador> findByUsername(String username) {
        return tatuadorRepository.findByUsername(username);
    }

    public Tatuador save(Tatuador tatuador) {
        tatuador.setPassword(passwordEncoder.encode(tatuador.getPassword()));
        return tatuadorRepository.save(tatuador);
    }

    public Tatuador update(Tatuador tatuador) {
        return tatuadorRepository.save(tatuador);
    }

    public void delete(Tatuador tatuador) {
        tatuadorRepository.delete(tatuador);
    }

    public boolean existsByUsername(String username) {
        return tatuadorRepository.existsByUsername(username);
    }
}