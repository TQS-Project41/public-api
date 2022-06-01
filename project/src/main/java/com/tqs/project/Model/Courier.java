package com.tqs.project.Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;



import javax.persistence.Table;
@Table(name="courier")
@Entity
public class Courier {
    
    @Id
    @Column(name = "user_id")
    private Long id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    
    @Column
     @NotNull(message = "Name é obrigatório")
    private String name;
    
    @Column
    private String photo;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthdate;
    
    @OneToMany(mappedBy="courier")
    private Set<Delivery> delivery;
     
    @OneToMany(mappedBy="courier")
    private Set<BusinessCourierInteractions> businessCourierInteractions;
   
    public Courier() {
        this.delivery = new HashSet<>();
        this.businessCourierInteractions = new HashSet<>();
    }
    

    public Courier(User user,  String name, String photo, Date birthdate,
            Set<Delivery> delivery, Set<BusinessCourierInteractions> businessCourierInteractions) {
        this.user = user;
        this.name = name;
        this.photo = photo;
        this.birthdate = birthdate;
        this.delivery = delivery;
        this.businessCourierInteractions = businessCourierInteractions;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    
    public Set<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(Set<Delivery> delivery) {
        this.delivery = delivery;
    }
    
    public Set<BusinessCourierInteractions> getBusinessCourierInteractions() {
        return businessCourierInteractions;
    }

    public void setBusinessCourierInteractions(Set<BusinessCourierInteractions> businessCourierInteractions) {
        this.businessCourierInteractions = businessCourierInteractions;
    }
    
    @Override
    public String toString() {
        return "Courier [birthdate=" + birthdate + ", name=" + name + ", photo=" + photo + ", user=" + user + "]";
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    
    

}
