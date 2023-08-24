package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="aceite_scr")
public class AceiteScr {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer aceite_id;

    @ManyToOne
    @JoinColumn(name = "proposal.proposal_id")
    private Proposal proposal_id;

    @Column
    private String geolocalizacao;

    @Column
    private String ip_publico_usuario;

    @Column
    private String dispositivo;

    @Column
    private String so;

    @Column
    private LocalDateTime data_hora;

}
