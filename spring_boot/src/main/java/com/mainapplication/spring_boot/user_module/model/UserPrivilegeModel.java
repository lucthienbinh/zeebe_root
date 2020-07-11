package com.mainapplication.spring_boot.user_module.model;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_privilege")
public class UserPrivilegeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String name;
 
    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    private Collection<UserRoleModel> roles;

    public UserPrivilegeModel(String name) {
        this.name = name;
    }
}