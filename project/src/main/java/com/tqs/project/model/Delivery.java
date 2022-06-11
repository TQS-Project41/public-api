package com.tqs.project.model;

import java.time.LocalDateTime;

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

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Table;

@Table(name="delivery")
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column
    private LocalDateTime deliveryTimestamp;

    @Embedded
    @NotNull
    @AttributeOverride(name="latitude", column=@Column(name="delivery_latitude", nullable=false))
    @AttributeOverride(name="longitude", column=@Column(name="delivery_longitude",nullable=false))
    private Address deliveryAddress;

    @Embedded
    @NotNull
    @AttributeOverride(name="name", column=@Column(name="client_name", nullable=false))
    @AttributeOverride(name="phoneNumber", column=@Column(name="client_phoneNumber", nullable=false))
    private DeliveryContact client;

    @ManyToOne
    @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="courier_id", nullable=false)
    private Courier courier;

    public Delivery() {
    }
    

    public Delivery(LocalDateTime deliveryTimestamp, Address deliveryAddress, DeliveryContact client, Shop shop, Courier courier) {
        this.deliveryTimestamp = deliveryTimestamp;
        this.deliveryAddress = deliveryAddress;
        this.client = client;
        this.shop = shop;
        this.courier = courier;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public LocalDateTime getDeliveryTimestamp() {
        return this.deliveryTimestamp;
    }

    public void setDeliveryTimestamp(LocalDateTime deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public Address getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryContact getClient() {
        return this.client;
    }

    public void setClient(DeliveryContact client) {
        this.client = client;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Courier getCourier() {
        return this.courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", deliveryTimestamp='" + getDeliveryTimestamp() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", client='" + getClient() + "'" +
            ", shop='" + getShop() + "'" +
            ", courier='" + getCourier() + "'" +
            "}";
    }    
    
}
