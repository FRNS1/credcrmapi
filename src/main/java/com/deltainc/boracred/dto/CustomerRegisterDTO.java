package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {

    private String is_cnpj;

    private String nome_completo;

    private String nome_fantasia;

    private String razao_social;

    private String cpf;

    private String cnpj;

    private String segmento;

    private LocalDate data_nascimento;

    private LocalDate data_abertura;

    private String escolaridade;

    private String genero;

    private String ocupacao;

    private String email;

    private String telefone;

    private String cep;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String estado;

    private String pais;

}
