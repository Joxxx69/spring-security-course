package com.spring.security.springsecuritycourse.persistence.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GrantedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grantedPermissionId;

    @ManyToOne
    @JoinColumn(
        name = "role_id",
        referencedColumnName = "roleId"
    )
    private Role role;

    @ManyToOne
    @JoinColumn(
        name = "operation_id",
        referencedColumnName = "operationId"
    )
    private Operation operation;
}
