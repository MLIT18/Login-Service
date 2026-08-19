package com.insightzz.loginservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;
}
