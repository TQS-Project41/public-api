package com.tqs.project.Model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="delivery")
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column
    private long delivery_timestamp;

    @Embedded
    @NotNull
    @AttributeOverrides({
        @AttributeOverride( name = "latitude", column = @Column(name = "delivery_latitude", nullable =false)),
        @AttributeOverride( name = "longitude", column = @Column(name = "delivery_longitude",nullable =false)),
      })
    private Address delivery_address;

    @Embedded
    @NotNull
    @AttributeOverrides({
        @AttributeOverride( name = "name", column = @Column(name = "client_name", nullable =false)),
        @AttributeOverride( name = "phoneNumber", column = @Column(name = "client_phoneNumber", nullable =false)),
      })
    private DeliveryContact client;

    @ManyToOne
    @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="courier_id", nullable=false)
    private Courier courier;

    public Delivery() {
    }
    

    public Delivery(long delivery_timestamp, Address delivery_address, DeliveryContact client, Shop shop,
            Courier courier) {
        this.delivery_timestamp = delivery_timestamp;
        this.delivery_address = delivery_address;
        this.client = client;
        this.shop = shop;
        this.courier = courier;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getDelivery_timestamp() {
        return delivery_timestamp;
    }

    public void setDelivery_timestamp(long delivery_timestamp) {
        this.delivery_timestamp = delivery_timestamp;
    }

    public Address getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(Address delivery_address) {
        this.delivery_address = delivery_address;
    }

    public DeliveryContact getClient() {
        return client;
    }

    public void setClient(DeliveryContact client) {
        this.client = client;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }


    @Override
    public String toString() {
        return "Delivery [client=" + client + ", courier=" + courier + ", delivery_address=" + delivery_address
                + ", delivery_timestamp=" + delivery_timestamp + ", id=" + id + ", shop=" + shop + ", timestamp="
                + timestamp + "]";
    }

    
    
}
