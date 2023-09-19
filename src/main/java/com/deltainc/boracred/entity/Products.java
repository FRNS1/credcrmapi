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
@Table(name="products")
public class Products {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence")
    private Integer products_id;

    @ManyToOne
    @JoinColumn(name = "proposal.proposal_id")
    private Proposal proposal_id;

    @Column
    private String product_name;

}
