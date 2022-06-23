package com.tqs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.Business;
import com.tqs.project.model.Shop;

@Repository
public interface ShopRepository   extends JpaRepository<Shop,Long> {

}
