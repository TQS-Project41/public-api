package com.tqs.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.tqs.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>  {

  public Optional<User> findByEmailAndPassword(String email, String password);
    
}
