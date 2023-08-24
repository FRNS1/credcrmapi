package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalUpdateDTO {

    private Integer proposal_id;

    private float valor_desejado;

    private float taxa;

    private float corban;

    private String status;

    private float montante;

    private float valor_liberado;

    private int prazo;

    private LocalDate data_abertura;

    private LocalDate data_primeira_parcela;

    private float total_juros;

    private String status_contrato;

    private String motivo_reprovacao;

    private String observacao_cliente;

    private String observacao_analista;

}
