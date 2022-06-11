package com.tqs.project.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="shop")
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "name é obrigatório")
    private String name;
    
    @NotNull(message = "Address é obrigatório")
    @Embedded
    @AttributeOverride(name="latitude", column=@Column(name="shop_latitude", nullable=false))
    @AttributeOverride(name="longitude", column=@Column(name="shop_longitude", nullable=false))
    private Address address;
    
    @ManyToOne
    @JoinColumn(name="business_id", nullable=false)
    private Business business;
    
    public Shop() {
    }

    public Shop(String name, Address address, Business business) {
        this.name = name;
        this.address = address;
        this.business = business;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Business getBusiness() {
        return this.business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", business='" + getBusiness() + "'" +
            "}";
    }

}
