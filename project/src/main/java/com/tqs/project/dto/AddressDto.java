package com.tqs.project.dto;

import javax.validation.constraints.NotEmpty;

public class AddressDto {
  
  @NotEmpty
  private String country;
  @NotEmpty
  private String zipcode;
  @NotEmpty
  private String city;
  @NotEmpty
  private String address;

  public String getCountry() {
    return this.country;
  }

  public String getZipcode() {
    return this.zipcode;
  }

  public String getCity() {
    return this.city;
  }

  public String getAddress() {
    return this.address;
  }

}
