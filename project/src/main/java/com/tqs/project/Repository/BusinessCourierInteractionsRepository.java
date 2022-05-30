package com.tqs.project.Repository;
import com.tqs.project.Model.BusinessCourierInteractions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCourierInteractionsRepository  extends JpaRepository<BusinessCourierInteractions,Long> {
    
}
