package com.tqs.project.dto;

import javax.validation.constraints.NotEmpty;

public class ShopDto {

  @NotEmpty
  private String name;

  private AddressDto address;

  public String getName() {
    return this.name;
  }

  public AddressDto getAddress() {
    return this.address;
  }
  
}
