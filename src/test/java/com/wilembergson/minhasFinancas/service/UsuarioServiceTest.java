package com.wilembergson.minhasFinancas.service;

import com.wilembergson.minhasFinancas.exceptions.ErroAutenticacao;
import com.wilembergson.minhasFinancas.exceptions.RegraNegocioException;
import com.wilembergson.minhasFinancas.model.entity.Usuario;
import com.wilembergson.minhasFinancas.model.repository.UsuarioRepository;
import com.wilembergson.minhasFinancas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveSalvarUmUsuario(){
        //Cenario
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //Ação
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        //Verificação
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSenha(){
        //Cenario
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //Ação
        Usuario result = service.autenticar(email, senha);

        //Verificação
        Assertions.assertThat(result).isNotNull();
    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveCadastrarUmUsuarioComEmailJaCadastrado(){
        //Cenario
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        //Ação
        service.salvarUsuario(usuario);

        //Verificação
        Mockito.verify(repository, Mockito.never()).save(usuario);
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado(){
        //Cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //Ação
        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));

        //Verificação
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuario não encontrado para o email informado.");
    }

    @Test
    public void deveLancarErroQuandoSenhaNaoBater(){
        //Cenario
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //Ação
        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "123"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        //Cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //Ação
        service.validarEmail("usuario@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancaErroAoValidarEmailQuandoExistirEmailCadastrado(){
        //Cenario
        Mockito.when(repository.existsByEmail((Mockito.anyString()))).thenReturn(true);

        //Ação
        service.validarEmail("email@email.com");
    }
}
