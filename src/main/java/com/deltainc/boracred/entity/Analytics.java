package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="analytics")
public class Analytics {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer analytics_id;

    @ManyToOne
    @JoinColumn(name = "proposal.proposal_id")
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "customer.customer_id")
    private Customer customer;

    @Column
    private int num_titulos_protestados;

    @Column
    private int score;

    @Column
    private int num_refins;

    @Column
    private float valor_cadins;

    @Column
    private float valor_iss;

    @Column
    private int num_processos;

    @Column
    private float valor_processos;

    @Column
    private int num_uf_processos;

    @Column
    private float divida_ativa;

    @Column
    private float valor_titulos_protestados;

    @Column
    private float risco;

    @Column
    private boolean pep;

}
