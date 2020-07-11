package com.mainapplication.spring_boot.user_module.service;

import java.util.Arrays;
import java.util.Optional;
import javax.ws.rs.NotFoundException;

import com.mainapplication.spring_boot.user_module.dao.UserRepository;
import com.mainapplication.spring_boot.user_module.dao.UserRoleRepository;
import com.mainapplication.spring_boot.user_module.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Iterable<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(int id) throws NotFoundException {
        Optional<UserModel> optionalEntity =  userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            return optionalEntity.get();
        } else {
            throw new NotFoundException();
        }
    }

    public String create(UserModel _user) {
        String userId = "";

        try {
            UserModel user = UserModel.builder()
                    .username(_user.getUsername())
                    .password(bCryptPasswordEncoder.encode(_user.getPassword()))
                    .enabled(true)
                    .roles(Arrays.asList(userRoleRepository.findByName("ROLE_USER").get()))
                    .email(_user.getEmail())
                    .first_name(_user.getFirst_name())
                    .last_name(_user.getLast_name())
                    .age(_user.getAge())
                    .job_title(_user.getJob_title())
                    .phone(_user.getPhone())
                    .build();
            userRepository.save(user);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created with id = " + userId;
    }

    public String update(int id, UserModel _user) {
        if (userRepository.findById(id) == null) {
            return "User not found";
        } else {
            try {
                UserModel user = userRepository.findById(id).get();
                user.setFirst_name(_user.getFirst_name());
                user.setLast_name(_user.getLast_name());
                user.setAge(_user.getAge());
                user.setJob_title(_user.getJob_title());
                user.setPhone(_user.getPhone());
                userRepository.save(user);
            }
            catch (Exception ex) {
                return "Error updating the user: " + ex.toString();
            }
            return "User succesfully updated!";
        }
       
    }

    public String delete(int id) {
        try {
            UserModel user = new UserModel(id);
            userRepository.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user: " + ex.toString();
        }
        return "User succesfully deleted!";
    }
}