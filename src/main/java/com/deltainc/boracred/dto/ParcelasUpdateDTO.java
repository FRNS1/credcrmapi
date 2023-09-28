package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelasUpdateDTO {

    private int parcela;

    private float valor_parcela;

    private String pago;

    private LocalDate data_pagamento;

}
