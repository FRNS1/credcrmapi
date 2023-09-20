package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrUpdateDTO {

    private Integer proposal_id;

    private Float vencer_valor_total;

    private Float vencer_ate_30_dias_vencidos_ate_14_dias;

    private Float vencer_31_60_dias;

    private Float vencer_61_90_dias;

    private Float vencer_91_180_dias;

    private Float vencer_181_360_dias;

    private Float vencer_acima_360_dias;

    private Float vencer_indeterminado;

    private Float vencido_total;

    private Float vencido_15_30_dias;

    private Float vencido_31_60_dias;

    private Float vencido_61_90_dias;

    private Float vencido_91_180_dias;

    private Float vencido_181_360_dias;

    private Float vencido_acima_360_dias;

    private Float prejuizo_total;

    private Float prejuizo_ate_12_meses;

    private Float prejuizo_acima_12_meses;

    private Float coobrigacao_total;

    private Float coobrigacao_assumida;

    private Float coobrigacao_prestadas;

    private Float creditos_liberar_total;

    private Float creditos_liberar_ate_360_dias;

    private Float creditos_liberar_acima_360_dias;

    private Float limites_credito_valor_total;

    private Float limites_credito_vencimento_ate_360_dias;

    private Float limites_credito_vencimento_acima_360_dias;

}
