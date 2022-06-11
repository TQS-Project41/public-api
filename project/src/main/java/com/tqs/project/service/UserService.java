package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.model.User;
import com.tqs.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = true)
    private UserRepository rep;


    public User save(User user) {
        return rep.save(user);
    }

    public List<User> saveAll(List<User> user) {
        return rep.saveAll(user);
    }
    
    public List<User> getAllUsers() {
        return rep.findAll();
    }

    public Optional<User> getUserById(long id) {
        return rep.findById(id);
    }
}