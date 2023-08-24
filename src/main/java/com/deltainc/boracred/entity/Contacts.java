package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer contact_id;

    @ManyToOne
    @JoinColumn(name = "customer.customer_id")
    private Customer customer_id;

    @Column
    private String telefone;

    @Column
    private String email;

}
