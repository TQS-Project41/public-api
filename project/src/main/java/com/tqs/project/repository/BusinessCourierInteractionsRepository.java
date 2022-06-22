package com.tqs.project.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.project.model.Business;
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.BusinessCourierInteractionsEventTypeEnum;
import com.tqs.project.model.Courier;

@Repository
public interface BusinessCourierInteractionsRepository  extends JpaRepository<BusinessCourierInteractions,Long> {
    
    public Optional<BusinessCourierInteractions> findFirstByBusinessAndCourierOrderByTimestampDesc(Business business, Courier courier);

    public List<BusinessCourierInteractions> findByBusinessAndCourierOrderByTimestampDesc(Business business, Courier courier);

    public List<BusinessCourierInteractions> findDistinctBusinessByCourierAndEventOrderByTimestampDesc(Courier courier, BusinessCourierInteractionsEventTypeEnum event);

    public List<BusinessCourierInteractions> findDistinctCourierByBusinessAndEventOrderByTimestampDesc(Business business, BusinessCourierInteractionsEventTypeEnum event);

}
