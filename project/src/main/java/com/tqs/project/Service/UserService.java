package com.tqs.project.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.Exception.UserAlreadyAssignedException;
import com.tqs.project.Model.User;
import com.tqs.project.Repository.UserRepository;

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

    /* *************** Talvez precisaremos mais tarde ***************
    public User getUserByUsername(String username) {
        return rep.findByUsername(username);
    } 
    */

    public User update(User user) throws UserAlreadyAssignedException {
        User existingUser = rep.findById( user.getId() ).orElse(null);

        if (existingUser != null) {
            // não irá ser possível alterar o username!
            if (user.getCourier() != null) existingUser.setCourier(user.getCourier());
            if (user.getBusiness() != null) existingUser.setBusiness(user.getBusiness());
            if (user.getPassword() != null) existingUser.setPassword(user.getPassword());

            return rep.save(existingUser);
        }
        return null;
       
    }
}