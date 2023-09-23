package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.internal.build.AllowSysOut;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "sociopj")
public class SocioPj {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer socio_id;

    @ManyToOne
    @JoinColumn(name = "customer.customer_id")
    private Customer customer;

    @Column
    private String nome_socio;

    @Column
    private String cpf_socio;

}
