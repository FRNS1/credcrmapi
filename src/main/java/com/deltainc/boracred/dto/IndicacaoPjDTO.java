package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndicacaoPjDTO {

    private String cnpj;

    private String razaoSocial;

    private String nomeFantasia;

    private String segmento;

    private String nomeSocio;

    private String cpfSocio;

    private String email;

    private String telefone;

    private Float receitaMedia;

    private Float valorDesejado;

    private Integer prazo;

    private Integer codigo_indicador;

    private LocalDate data_abertura;

    private String nome_referencia;

    private String documento;

    private String email_referencia;

    private String telefone_referencia;

}
