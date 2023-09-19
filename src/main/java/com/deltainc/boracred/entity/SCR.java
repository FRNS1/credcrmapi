package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="scr")
public class SCR {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence")
    private Integer scr_id;

    @ManyToOne
    @JoinColumn(name = "proposal.proposal_id")
    private Proposal proposal;

    @Column
    private Float vencer_valor_total;

    @Column
    private Float vencer_ate_30_dias_vencidos_ate_14_dias;

    @Column
    private Float vencer_31_60_dias;

    @Column
    private Float vencer_61_90_dias;

    @Column
    private Float vencer_181_360_dias;

    @Column
    private Float vencer_acima_360_dias;

    @Column
    private Float vencer_indeterminado;

    @Column
    private Float vencido_total;

    @Column
    private Float vencido_15_30_dias;

    @Column
    private Float vencido_31_60_dias;

    @Column
    private Float vencido_61_90_dias;

    @Column
    private Float vencido_91_180_dias;

    @Column
    private Float vencido_181_360_dias;

    @Column
    private Float vencido_acima_360_dias;

    @Column
    private Float prejuizo_total;

    @Column
    private Float prejuizo_ate_12_meses;

    @Column
    private Float prejuizo_acima_12_meses;

    @Column
    private Float coobrigacao_total;

    @Column
    private Float coobrigacao_assumida;

    @Column
    private Float coobrigacao_prestadas;

    @Column
    private Float creditos_liberar_total;

    @Column
    private Float creditos_liberar_ate_360_dias;

    @Column
    private Float creditos_liberar_acima_360_dias;

    @Column
    private Float limites_credito_valor_total;

    @Column
    private Float limites_credito_vencimento_ate_360_dias;

    @Column
    private Float limites_credito_vencimento_acima_360_dias;
}
