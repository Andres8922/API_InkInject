package com.example.Service;

import com.example.Entity.Cliente;
import com.example.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> findByUsername(String username) {
        return clienteRepository.findByUsername(username);
    }

    public Cliente save(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    public boolean existsByUsername(String username) {
        return clienteRepository.existsByUsername(username);
    }
}