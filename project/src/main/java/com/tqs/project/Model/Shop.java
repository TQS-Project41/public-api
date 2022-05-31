package com.tqs.project.Model;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @AttributeOverrides({
        @AttributeOverride( name = "latitude", column = @Column(name = "shop_latitude", nullable =false)),
        @AttributeOverride( name = "longitude", column = @Column(name = "shop_longitude", nullable =false)),
      })
    private Address shop_address;

    @OneToMany(mappedBy="shop")
    private Set<Delivery> delivery;
    
      @ManyToOne
      @JoinColumn(name="business_id", nullable=false)
      private Business business;
  
  
      
    
    public Shop() {
    }


    public Shop(String name,
            Address shop_address, Business business,
            Set<Delivery> delivery) {
        this.name = name;
        this.shop_address = shop_address;
        this.business = business;
        this.delivery = delivery;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Address getShop_address() {
        return shop_address;
    }


    public void setShop_address(Address shop_address) {
        this.shop_address = shop_address;
    }


    public Business getBusiness() {
        return business;
    }


    public void setBusiness(Business business) {
        this.business = business;
    }


    public Set<Delivery> getDelivery() {
        return delivery;
    }


    public void setDelivery(Set<Delivery> delivery) {
        this.delivery = delivery;
    }


    
}
