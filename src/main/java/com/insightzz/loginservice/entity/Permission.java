package com.insightzz.loginservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permission_master")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "permission_code", nullable = false, unique = true)
    private String permissionCode;

    @Column(name = "module", nullable = false)
    private String module;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "is_active")
    private Boolean isActive;
}