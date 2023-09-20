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
@Table(name="grupos")
public class Grupos {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer grupo_id;

    @Column
    private String nome_grupo;

}
