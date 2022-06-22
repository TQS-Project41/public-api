package com.tqs.project.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeliveryDto {

  private String deliveryTimestamp;

  private AddressDto shopAddress;

  private AddressDto address;

  private String clientName;

  private String clientPhoneNumber;

  public LocalDateTime getDeliveryTimestamp() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    return LocalDateTime.parse(this.deliveryTimestamp, formatter);
  }

  public AddressDto getShopAddress() {
    return this.shopAddress;
  }

  public AddressDto getAddress() {
    return this.address;
  }

  public String getClientName() {
    return this.clientName;
  }

  public String getClientPhoneNumber() {
    return this.clientPhoneNumber;
  }
  
}
