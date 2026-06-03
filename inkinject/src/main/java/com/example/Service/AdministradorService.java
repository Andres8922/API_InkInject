package com.example.Service;

import com.example.Entity.Administrador;
import com.example.Repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    public Optional<Administrador> findByUsername(String username) {
        return administradorRepository.findByUsername(username);
    }

    public Administrador save(Administrador administrador) {
        administrador.setPassword(passwordEncoder.encode(administrador.getPassword()));
        return administradorRepository.save(administrador);
    }

    public Administrador update(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void delete(Administrador administrador) {
        administradorRepository.delete(administrador);
    }

    public boolean existsByUsername(String username) {
        return administradorRepository.existsByUsername(username);
    }
}