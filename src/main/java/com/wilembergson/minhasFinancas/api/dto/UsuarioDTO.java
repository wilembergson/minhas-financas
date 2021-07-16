package com.wilembergson.minhasFinancas.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String email;
    private String nome;
    private String senha;
}
