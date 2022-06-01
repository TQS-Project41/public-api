package com.tqs.project.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Table(name="business")
@Entity
public class Business {
    
    @Id
    @Column(name = "user_id")
    private Long id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    
    
    @OneToMany(mappedBy="business")
    private Set<Shop> shop;
    
    @OneToMany(mappedBy="business")
    private Set<BusinessCourierInteractions> businessCourierInteractions;
    

    public Business() {
        this.shop = new HashSet<>();
        this.businessCourierInteractions = new HashSet<>();
    }

    
    public Business( User user, Set<Shop> shop, Set<BusinessCourierInteractions> businessCourierInteractions) {
        this.user = user;
        this.shop = shop;
        this.businessCourierInteractions = businessCourierInteractions;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Shop> getShop() {
        return shop;
    }

    public void setShop(Set<Shop> shop) {
        this.shop = shop;
    }

    public Set<BusinessCourierInteractions> getBusinessCourierInteractions() {
        return businessCourierInteractions;
    }

    public void setBusinessCourierInteractions(Set<BusinessCourierInteractions> businessCourierInteractions) {
        this.businessCourierInteractions = businessCourierInteractions;
    }
    
    
}
