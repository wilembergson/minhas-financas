package com.wilembergson.minhasFinancas.service;

import com.wilembergson.minhasFinancas.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

    Optional<Usuario> obterPorId(Long id);

    List<Usuario> listarUsuarios();
}
