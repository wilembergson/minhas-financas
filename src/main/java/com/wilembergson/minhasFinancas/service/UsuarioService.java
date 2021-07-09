package com.wilembergson.minhasFinancas.service;

import com.wilembergson.minhasFinancas.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
