package com.tqs.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="business")
@Entity
public class Business {
    
    @Id
    @Column(name = "user_id")
    private long id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Business() {
    }
    
    public Business( User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }
    
}
