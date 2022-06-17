package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.model.User;
import com.tqs.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = true)
    private UserRepository rep;

    public User save(User user) {
        return rep.save(user);
    }
    
    public List<User> getAllUsers() {
        return rep.findAll();
    }

    public Optional<User> getUserById(long id) {
        return rep.findById(id);
    }

    public Optional<User> getByEmailAndPassword(String email, String password) {
        return rep.findByEmailAndPassword(email, password);
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            return Optional.empty();

        Object principal = authentication.getPrincipal();

        if (principal == null)
            return Optional.empty();

        return Optional.of((User) principal);
    }
}