package com.maz.academy.core.models;

import com.maz.academy.user.admin.Admin;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "permissions")
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;

    private String permissionName;
    private String permissionDescription;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private Admin admin;
}
