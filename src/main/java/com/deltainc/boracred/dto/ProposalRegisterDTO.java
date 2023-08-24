package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalRegisterDTO {

    private Integer customer_id;

    private float valor_desejado;

    private int prazo;

    private String observacao_cliente;

}
