// src/main/java/com/tr/rms/rbac/repository/RoleRepository.java
package com.tr.rms.rbac.repository;

import com.tr.rms.rbac.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query("SELECT r FROM Role r JOIN r.userRoles ur WHERE ur.userId = :userId AND r.active = true")
    Set<Role> findActiveByUserId(@Param("userId") UUID userId);
}