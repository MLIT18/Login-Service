package com.insightzz.loginservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_mob_no")
    private String userMobNo;

    @Column(name = "user_designation")
    private String userDesignation;

    @Column(name = "user_doj")
    private LocalDate userDoj;

    @Column(name = "user_dol")
    private LocalDate userDol;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;
}