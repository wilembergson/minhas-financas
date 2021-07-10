package com.wilembergson.minhasFinancas.exceptions;

public class ErroAutenticacao extends RuntimeException{

    public ErroAutenticacao(String menssagem){
        super(menssagem);
    }
}
