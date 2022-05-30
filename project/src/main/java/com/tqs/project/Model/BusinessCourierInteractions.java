package com.tqs.project.Model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;




@Table(name="business_courier_interactions")
@Entity
public class BusinessCourierInteractions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name="business_id", nullable=false)
    private Business business;

    @ManyToOne
    @JoinColumn(name="courier_id", nullable=false)
    private Courier courier;
    
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private BusinessCourierInteractionsEventType event;
    
    public BusinessCourierInteractions() {
    }

    
    
    public BusinessCourierInteractions(Business business, Courier courier, BusinessCourierInteractionsEventType event) {
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    public BusinessCourierInteractionsEventType getEvent() {
        return event;
    }

    public void setEvent(BusinessCourierInteractionsEventType event) {
        this.event = event;
    }

  
}
