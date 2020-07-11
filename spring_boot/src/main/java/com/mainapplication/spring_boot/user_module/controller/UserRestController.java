package com.mainapplication.spring_boot.user_module.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.mainapplication.spring_boot.user_module.model.UserModel;
import com.mainapplication.spring_boot.user_module.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@Validated
public class UserRestController {

    // Private fields

    @Autowired
    private UserService service;

    @GetMapping(path = "/list")
    ResponseEntity<?> findAll() throws Exception {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        // return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/{id}")
    // @RequestParam int id
    ResponseEntity<?> findUserById(@PathVariable("id") int id) throws Exception {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    ResponseEntity<?> create(@Valid @RequestBody UserModel user) throws Exception {
        return new ResponseEntity<>(service.create(user), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid UserModel user) throws Exception {
        return new ResponseEntity<>(service.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable("id") int id) throws Exception{
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    // https://examples.javacodegeeks.com/spring-boot-bean-validation-example/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
    
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        return errors;
    }
}