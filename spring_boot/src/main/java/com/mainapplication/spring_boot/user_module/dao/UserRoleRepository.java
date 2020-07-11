package com.mainapplication.spring_boot.user_module.dao;

import java.util.Optional;

import com.mainapplication.spring_boot.user_module.model.UserRoleModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false) 
public interface UserRoleRepository extends JpaRepository<UserRoleModel, Integer> {
    
    Optional<UserRoleModel> findByName(String name);
}