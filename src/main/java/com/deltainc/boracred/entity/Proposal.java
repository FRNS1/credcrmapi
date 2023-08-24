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
@Table(name="proposal")
public class Proposal {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer proposal_id;

    @ManyToOne
    @JoinColumn(name = "customer.customer_id")
    private Customer customer_id;

    @Column
    private float valor_desejado;

    @Column
    private float taxa;

    @Column
    private float corban;

    @Column
    private String status;

    @Column
    private float montante;

    @Column
    private float valor_liberado;

    @Column
    private int prazo;

    @Column
    private LocalDate data_abertura;

    @Column
    private LocalDate data_primeira_parcela;

    @Column
    private float total_juros;

    @Column
    private String status_contrato;

    @Column
    private String motivo_reprovacao;

    @Column
    private String observacao_cliente;

    @Column
    private String observacao_analista;

}
