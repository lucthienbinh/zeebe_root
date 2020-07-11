package com.mainapplication.spring_boot.user_module;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.mainapplication.spring_boot.user_module.dao.UserPrivilegeRepository;
import com.mainapplication.spring_boot.user_module.dao.UserRepository;
import com.mainapplication.spring_boot.user_module.dao.UserRoleRepository;
import com.mainapplication.spring_boot.user_module.model.UserModel;
import com.mainapplication.spring_boot.user_module.model.UserPrivilegeModel;
import com.mainapplication.spring_boot.user_module.model.UserRoleModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitDataWhenStartUp implements CommandLineRunner{

    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private UserRoleRepository userRoleRepository;
  
    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    private UserPrivilegeModel createPrivilegeIfNotFound(String name) {
  
        UserPrivilegeModel privilege = null;
        if (!userPrivilegeRepository.findByName(name).isPresent()) {
            privilege = new UserPrivilegeModel(name);
            userPrivilegeRepository.save(privilege);
        } else {
            privilege = userPrivilegeRepository.findByName(name).get();
        }
        return privilege;
    }
 
    @Transactional
    private UserRoleModel createRoleIfNotFound(
      String name, Collection<UserPrivilegeModel> privileges) {
  
        UserRoleModel role = null;
        if (!userRoleRepository.findByName(name).isPresent()) {
            role = new UserRoleModel(name);
            role.setPrivileges(privileges);
            userRoleRepository.save(role);
        } else {
            role = userRoleRepository.findByName(name).get();
        }
        return role;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(alreadySetup)
        {
            return;
        }

        UserPrivilegeModel readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
        UserPrivilegeModel writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
  
        List<UserPrivilegeModel> adminPrivileges = Arrays.asList( readPrivilege, writePrivilege);        

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        UserRoleModel adminRole = userRoleRepository.findByName("ROLE_ADMIN").get();
        UserRoleModel userRole = userRoleRepository.findByName("ROLE_USER").get();
        UserModel user = null;
        user = UserModel.builder()
            .username("admin").password(bCryptPasswordEncoder.encode("123456"))
            .enabled(true).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true)
            .roles(Arrays.asList(adminRole))
            .email("admin@gmail.com").first_name("First name").last_name("Last name").age(10).job_title("Administrator").phone("12345678")
            .build();
        if (!userRepository.findByUsername("admin").isPresent())
        {
            userRepository.save(user);
        }
        user = UserModel.builder()
            .username("user").password(bCryptPasswordEncoder.encode("123456"))
            .enabled(true).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true)
            .roles(Arrays.asList(userRole))
            .email("user@gmail.com").first_name("First name").last_name("Last name").age(10).job_title("Normal user").phone("12345678")
            .build();
        if (!userRepository.findByUsername("user").isPresent())
        {
            userRepository.save(user);
        }
        alreadySetup = true;
    }
    
}