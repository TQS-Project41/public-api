package com.tqs.project.Repository;

import com.tqs.project.Model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>  {

  public Optional<User> findByEmailAndPassword(String email, String password);
    
}
