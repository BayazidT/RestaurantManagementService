package com.tr.rms.rbac.repository;

import com.tr.rms.rbac.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    @Query("SELECT p FROM Permission p JOIN p.rolePermissions rp WHERE rp.roleId = :roleId")
    Set<Permission> findByRoleId(UUID roleId);
}