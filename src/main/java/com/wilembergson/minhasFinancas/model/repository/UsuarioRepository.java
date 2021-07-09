package com.wilembergson.minhasFinancas.model.repository;

import com.wilembergson.minhasFinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
}
