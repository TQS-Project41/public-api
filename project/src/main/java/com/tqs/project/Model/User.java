package com.tqs.project.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import com.tqs.project.Exception.UserAlreadyAssignedException;

import lombok.Data;
import lombok.val;




import javax.persistence.Table;
@Table(name="user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Business business;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Courier courier;

    @Column
    @NotNull(message = "Password é obrigatório")
    private String password;
    
    @Column(unique=true)
    @NotNull(message = "Username é obrigatório")
    private String username;

    private  int counter =0;

    public User() {
    }

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) throws UserAlreadyAssignedException {
        if (counter==0){
            this.courier = courier;
            counter=1;
        }
        else{
            throw new UserAlreadyAssignedException("Este utilizador já se encontra com funcionalidades");
        }
        
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) throws UserAlreadyAssignedException {
        if (counter==0){
            this.business = business;
            counter=1;
        }
        else{
            throw new UserAlreadyAssignedException("Este utilizador já se encontra com funcionalidades");
        }
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User [id=" + id +  ", username=" + username + "]";
    }

    
}
