package com.tqs.project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;




@Table(name="business_courier_interactions")
@Entity
public class BusinessCourierInteractions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="business_id", nullable=false)
    private Business business;

    @ManyToOne
    @JoinColumn(name="courier_id", nullable=false)
    private Courier courier;
    
    @Enumerated(EnumType.ORDINAL)
    @Column
    @NotNull
    private BusinessCourierInteractionsEventTypeEnum event;
    
    public BusinessCourierInteractions() {
    }
    
    public BusinessCourierInteractions(Business business, Courier courier, BusinessCourierInteractionsEventTypeEnum event) {
        this.business = business;
        this.courier = courier;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public BusinessCourierInteractionsEventTypeEnum getEvent() {
        return event;
    }

    public void setEvent(BusinessCourierInteractionsEventTypeEnum event) {
        this.event = event;
    }
  
}
