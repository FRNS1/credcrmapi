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
    private Integer proposalId;

    @ManyToOne
    @JoinColumn(name = "customer.customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "users.user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "users.analista_id")
    private Users analista;

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
    private float renda_media;

    @Column
    private String status_contrato;

    @Column
    private String motivo_reprovacao;

    @Column(length = 10000)
    private String observacao_cliente;

    @Column(length = 10000)
    private String observacao_analista;

}
