package com.tqs.project.Repository;

import com.tqs.project.Model.Courier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier,Long> {
    
}
