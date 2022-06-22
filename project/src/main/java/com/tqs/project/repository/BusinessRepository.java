package com.tqs.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business,Long>  {
    public Optional<Business> findById(Long id);
}
