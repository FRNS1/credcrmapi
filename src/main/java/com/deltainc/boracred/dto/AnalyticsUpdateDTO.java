package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsUpdateDTO {

    private Integer proposalId;

    private Integer numTitulosProtestados;

    private Integer score;

    private Integer numRefins;

    private float valorCadins;

    private float valorIss;

    private Integer numProcessos;

    private float valorProcessos;

    private Integer numUfProcessos;

    private float dividaAtiva;

    private float valorTitulosProtestados;

    private float risco;

    private boolean pep;

    private Integer numChequesDevolvidos;

    private float valorChequesDevolvidos;

    private float valorPefins;

    private Integer numPefins;

    private Integer empresasNaoInformadas;

}
