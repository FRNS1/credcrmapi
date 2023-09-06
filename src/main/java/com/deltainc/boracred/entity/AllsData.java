package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="allsdata")
public class AllsData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer search_id;

    @ManyToOne
    @JoinColumn(name = "proposal.proposal_id")
    private Proposal proposal;

    @Column
    private Integer num_pendencias_financeiras;

    @Column
    private Float valor_pendencias_financeiras;

    @Column
    private Integer num_recuperacoes;

    @Column
    private Float valor_recuperacoes;

    @Column
    private Integer num_cheque_sem_fundo;

    @Column
    private Integer num_protestos;

    @Column
    private Float valor_protestos;

    @Column
    private Float limite_sugerido;

    @Column
    private Integer num_restricoes;

    @Column
    private Float valor_restricoes;

}
