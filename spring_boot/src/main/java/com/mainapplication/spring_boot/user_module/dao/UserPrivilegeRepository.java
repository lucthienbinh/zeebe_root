package com.mainapplication.spring_boot.user_module.dao;

import java.util.Optional;

import com.mainapplication.spring_boot.user_module.model.UserPrivilegeModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false) 
public interface UserPrivilegeRepository extends JpaRepository<UserPrivilegeModel,Integer> {
    Optional<UserPrivilegeModel> findByName(String name);
}