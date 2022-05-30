package com.tqs.project.Repository;

import com.tqs.project.Model.Business;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business,Long>  {
    
}
