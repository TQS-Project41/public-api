package com.tqs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier,Long> {
    
}
