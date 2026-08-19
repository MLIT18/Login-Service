package com.insightzz.loginservice.repository;

import com.insightzz.loginservice.entity.RolePermission;
import com.insightzz.loginservice.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository
        extends JpaRepository<RolePermission, RolePermissionId> {

    @Query("""
        SELECT p.permissionCode
        FROM RolePermission rp
        JOIN Permission p
            ON p.id = rp.id.permissionId
        JOIN Role r
            ON r.id = rp.id.roleId
        WHERE UPPER(r.roleName) = UPPER(:role)
          AND r.isActive = true
          AND p.isActive = true
    """)
    List<String> findPermissionsByRole(
            @Param("role") String role
    );
}
