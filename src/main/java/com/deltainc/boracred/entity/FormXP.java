package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="formxp")
public class FormXP {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer formXpId;
    @Column
    private String nome_completo;
    @Column
    private String email;
    @Column
    private String email_confirmacao;
    @Column
    private String cpf;
    @Column
    private LocalDate data_nascimento;
    @Column
    private String celular;
    @Column
    private String rg;
    @Column
    private String orgao_emissor;
    @Column
    private String estado_emissao;
    @Column
    private LocalDate data_emissao;
    @Column
    private String nome_mae;
    @Column
    private String nome_pai;
    @Column
    private String estado_civil;
    @Column
    private String nacionalidade;
    @Column
    private String estado_nascimento;
    @Column
    private String cidade_nascimento;
    @Column
    private String genero;
    @Column
    private Boolean vinculado_xp;
    @Column
    private Boolean us_person;
    @Column
    private Boolean pep;
    @Column
    private String cep;
    @Column
    private String endereco;
    @Column
    private String bairro;
    @Column
    private String numero;
    @Column
    private String estado;
    @Column
    private String cidade;
    @Column
    private String complemento;
    @Column
    private Float renda_mensal;
    @Column
    private Float aplicacoes_financeiras;
    @Column
    private Float bens_imoveis;
    @Column
    private Float bens_moveis;
    @Column
    private Float outros_rendimentos;
    @Column
    private List<String> origem_recursos;
    @Column
    private List<String> produtos_xp;
    @Column
    private Boolean empregado;
    @Column
    private String nome_empresa;
    @Column
    private String cnpj;
}
