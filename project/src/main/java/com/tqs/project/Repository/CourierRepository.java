package com.tqs.project.Repository;

import com.tqs.project.Model.Courier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier,Long> {
    
}