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
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer user_id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String empresa;

    @Column
    private String setor;

    @ManyToOne
    @JoinColumn(name = "grupos.grupo_id")
    private Grupos grupo_id;

}
