package com.tqs.project.Repository;

import com.tqs.project.Model.Shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository   extends JpaRepository<Shop,Long> {
    
}
