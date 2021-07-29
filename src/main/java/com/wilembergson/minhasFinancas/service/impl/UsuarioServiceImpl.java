package com.wilembergson.minhasFinancas.service.impl;

import com.wilembergson.minhasFinancas.exceptions.ErroAutenticacao;
import com.wilembergson.minhasFinancas.exceptions.RegraNegocioException;
import com.wilembergson.minhasFinancas.model.entity.Usuario;
import com.wilembergson.minhasFinancas.model.repository.UsuarioRepository;
import com.wilembergson.minhasFinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
        }

        Usuario usuarioSalvar = usuario.get();
        boolean valid = encoder.matches(senha, usuarioSalvar.getSenha());

        if(valid){
            return usuarioSalvar;
        }else{
            throw new ErroAutenticacao(("Senha inválida."));
        }
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Já exite um usuário cadastrado com este email.");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios;
    }
}
