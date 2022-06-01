package com.tqs.project.Repository;

import com.tqs.project.Model.Shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository   extends JpaRepository<Shop,Long> {
    
}
