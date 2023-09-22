package com.deltainc.boracred.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="logs")
public class Logs {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer logId;

    @ManyToOne
    @JoinColumn(name = "users.user_id")
    private Users user;

    @Column
    private Integer target;

    @Column
    private String target_type;

    @Column
    private LocalDateTime action_date;

    @Column
    private String action;

    @Column(length = 100000)
    private String old_value;

    @Column(length = 100000)
    private String new_value;

}