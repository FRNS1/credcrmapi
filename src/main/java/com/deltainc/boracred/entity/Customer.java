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
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer customer_id;

    @Column
    private boolean is_cnpj;

    @Column
    private String cnpj;

    @Column
    private String cpf;

    @Column
    private String nome_completo;

    @Column
    private String nome_fantasia;

    @Column
    private String razao_social;

    @Column
    private String idade;

    @Column
    private String genero;

    @Column
    private String escolaridade;

    @Column
    private String ocupacao;

    @Column
    private String segmento;

    @Column
    private LocalDate data_abertura;

    @Column
    private LocalDate data_nascimento;

    @Column
    private String business;

    @ManyToOne
    @JoinColumn(name = "users.user_id")
    private Users created_by;
}
