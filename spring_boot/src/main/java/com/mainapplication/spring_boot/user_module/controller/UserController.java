package com.mainapplication.spring_boot.user_module.controller;

import java.util.Arrays;
import java.util.List;

import com.mainapplication.spring_boot.user_module.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private String folderName = "user-module/";

    @Autowired
    private UserService service;

    @GetMapping("/list")
    public String userList(Model model ) {
        model.addAttribute("users", service.findAll());
        List<Integer> userIds = Arrays.asList(1,2,3,4);
        model.addAttribute("userIds", userIds);
        return folderName + "user-list";
    }
}