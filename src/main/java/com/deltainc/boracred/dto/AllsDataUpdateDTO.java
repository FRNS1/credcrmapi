package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllsDataUpdateDTO {

    private Integer proposal_id;

    private Integer num_pendencias_financeiras;

    private Float valor_pendencias_financeiras;

    private Integer num_recuperacoes;

    private Float valor_recuperacoes;

    private Integer num_cheque_sem_fundo;

    private Integer num_protestos;

    private Float valor_protestos;

    private Float limite_sugerido;

    private Integer num_restricoes;

    private Float valor_restricoes;

}
