package com.tqs.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;


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
   
    public Courier() {
    }

    public Courier(User user,  String name, String photo, Date birthdate) {
        this.user = user;
        this.name = name;
        this.photo = photo;
        this.birthdate = birthdate;
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
