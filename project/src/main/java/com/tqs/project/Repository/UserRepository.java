package com.tqs.project.Repository;

import com.tqs.project.Model.User;

import org.springframework.data.jpa.repository.JpaRepository; 

public interface UserRepository extends JpaRepository<User,Long>  {
    
}
