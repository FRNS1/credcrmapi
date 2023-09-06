package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsRegisterDTO {

    private int analytics;

    private int proposal;

    private int customer;

    private int num_titulos_protestados;

    private int score;

    private int num_refins;

    private float valor_cadins;

    private float valor_iss;

    private int num_processos;

    private float valor_processos;

    private int num_uf_processos;

    private float divida_ativa;

    private float valor_titulos_protestados;

    private float risco;

    private boolean pep;

    private int num_cheques_devolvidos;

    private float valor_cheques_devolvidos;

    private float valor_pefins;

    private int num_pefins;

    private int empresas_nao_informadas;

}
