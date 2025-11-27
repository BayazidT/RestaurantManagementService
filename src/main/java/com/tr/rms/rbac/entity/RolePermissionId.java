// RolePermissionId.java
package com.tr.rms.rbac.entity;

import java.io.Serializable;
import java.util.UUID;

public record RolePermissionId(UUID roleId, UUID permissionId) implements Serializable {}