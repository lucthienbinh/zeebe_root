package com.mainapplication.spring_boot.user_module.model;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_role")
public class UserRoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Collection<UserModel> users;
 
    @ManyToMany
    @JoinTable(
        name = "user_roles_privileges_join_table", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Collection<UserPrivilegeModel> privileges;   

    public UserRoleModel(String name) {
        this.name = name;
    }
}