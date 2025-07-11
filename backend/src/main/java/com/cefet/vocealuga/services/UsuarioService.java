package com.cefet.vocealuga.services;

import com.cefet.vocealuga.entities.Usuario;
import com.cefet.vocealuga.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository,
                          PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario saveUser(String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Usuario user = new Usuario(null, email, encodedPassword);
        return repository.save(user);
    }

    public Usuario findByUsername(String username) {
        return repository.findByEmail(username);
    }
}
