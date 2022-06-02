package com.tqs.project.Model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name="business_courier_interactions_event_type")
@Entity
public class BusinessCourierInteractionsEventType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column
    @NotNull(message = "Description é obrigatório")
    private BusinessCourierInteractionsEventTypeEnum description;

    @OneToMany(mappedBy="event")
    private Set<BusinessCourierInteractions> businessCourierInteractions;
    
    public BusinessCourierInteractionsEventType() {
        this.businessCourierInteractions=new HashSet<>();
    }
    
    public BusinessCourierInteractionsEventType(BusinessCourierInteractionsEventTypeEnum description) {
        this.description = description;
        this.businessCourierInteractions=new HashSet<>();

    }

    
    public BusinessCourierInteractionsEventType(
             BusinessCourierInteractionsEventTypeEnum description,
            Set<BusinessCourierInteractions> businessCourierInteractions) {
        this.description = description;
        this.businessCourierInteractions = businessCourierInteractions;
    }

    public long getId() {
        return id;
    }

    
    public Set<BusinessCourierInteractions> getBusinessCourierInteractions() {
        return businessCourierInteractions;
    }

    public void setBusinessCourierInteractions(Set<BusinessCourierInteractions> businessCourierInteractions) {
        this.businessCourierInteractions = businessCourierInteractions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BusinessCourierInteractionsEventTypeEnum getDescription() {
        return description;
    }

    public void setDescription(BusinessCourierInteractionsEventTypeEnum description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BusinessCourierInteractionsEventType [description=" + description + ", id=" + id + "]";
    }

    

}
