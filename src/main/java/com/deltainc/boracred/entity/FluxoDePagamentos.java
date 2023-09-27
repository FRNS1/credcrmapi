package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class FluxoDePagamentos {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer parcela_id;

    @ManyToOne
    @JoinColumn(name = "proposal_proposal_id")
    private Proposal proposal;

    @Column
    private int num_parcela;

    @Column
    private LocalDate vencimento;

    @Column
    private float saldo_devedor;

    @Column
    private float amortizacao;

    @Column
    private float juros;

    @Column
    private float pagamento;

    @Column
    private String pago;

    @Column
    private float valor_parcela_fixa;

    @Column
    private LocalDate data_pagamento;

    @Column
    private String boleto;

}
