package com.tqs.project.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;
@Table(name="delivery_status")
@Entity
public class DeliveryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column
    @NotNull(message = "Description é obrigatório")
    private DeliveryStatusEnum description;

    public DeliveryStatus() {
        }

    public DeliveryStatus(DeliveryStatusEnum description) {
        this.description = description;
    }
    

    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DeliveryStatusEnum getDescription() {
        return description;
    }

    public void setDescription(DeliveryStatusEnum description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DeliveryStatus [description=" + description + ", id=" + id + "]";
    }
    
    


}
