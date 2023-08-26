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
@Table(name="files")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer files_id;

    @ManyToOne
    @JoinColumn(name="proposal.proposal_id")
    private Proposal proposal_id;

    @Column
    private String tipo_arquivo;

    @Column
    private String url_arquivo;

    @Column
    private LocalDate uploaded_in;

    @Column
    private String file_name;

}
