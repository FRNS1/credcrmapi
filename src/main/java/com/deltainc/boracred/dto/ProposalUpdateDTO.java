package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalUpdateDTO {

    private Integer proposal_id;

    private float valor_desejado;

    private float taxa;

    private float corban;

    private String status;

    private float montante;

    private float valor_liberado;

    private int prazo;

    private LocalDate data_abertura;

    private LocalDate data_primeira_parcela;

    private float total_juros;

    private String status_contrato;

    private String motivo_reprovacao;

    private String observacao_cliente;

    private String observacao_analista;

    private Integer num_titulos_protestados;

    private Integer score;

    private Integer num_refins;

    private float valor_cadins;

    private float valor_iss;

    private Integer num_processos;

    private float valor_processos;

    private Integer num_uf_processos;

    private float divida_ativa;

    private float valor_titulos_protestados;

    private float risco;

    private boolean pep;


}
