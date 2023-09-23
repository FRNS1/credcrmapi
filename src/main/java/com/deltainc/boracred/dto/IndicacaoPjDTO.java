package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Float receitaMedia;

    private Float valorDesejado;

    private Integer prazo;

    private String geolocalizacao;

}
