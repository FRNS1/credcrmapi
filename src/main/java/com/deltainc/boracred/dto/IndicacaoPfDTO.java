package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndicacaoPfDTO {

    private String nome;

    private String cpf;

    private String email;

    private String profissao;

    private Float rendaMedia;

    private Float valorDesejado;

    private Integer prazo;

    private String geolocalizacao;

}
