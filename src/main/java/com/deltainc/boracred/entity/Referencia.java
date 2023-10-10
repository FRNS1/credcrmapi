package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="referencia")
public class Referencia {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer referenciaId;

    @ManyToOne
    @JoinColumn(name = "customer.customer_id")
    private Customer customer;

    @Column
    private String nomeCompleto;

    @Column
    private String email;

    @Column
    private String cpf;

    @Column
    private String telefone;

}
