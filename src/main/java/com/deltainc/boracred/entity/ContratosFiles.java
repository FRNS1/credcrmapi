package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="contratos_files")

public class ContratosFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer contratos_files_id;

    @ManyToOne
    @JoinColumn(name="proposal.proposal_id")
    private Proposal proposal;

    @Column
    private String path_file;
}

