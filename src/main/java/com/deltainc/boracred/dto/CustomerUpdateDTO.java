package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {

    private Integer customer_id;

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

}
