package com.wilembergson.minhasFinancas.service;

import com.wilembergson.minhasFinancas.exceptions.RegraNegocioException;
import com.wilembergson.minhasFinancas.model.entity.Usuario;
import com.wilembergson.minhasFinancas.model.repository.UsuarioRepository;
import com.wilembergson.minhasFinancas.service.impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService service;

    @MockBean
    UsuarioRepository repository;

    @Before
    public void setUp(){
        service = new UsuarioServiceImpl(repository);
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
