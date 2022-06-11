package com.tqs.project.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CourierDto {

  private String email;

  private String password;

  private String name;

  private String photo;

  private String birthdate;

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public String getName() {
    return this.name;
  }

  public String getPhoto() {
    return this.photo;
  }

  public LocalDate getBirthdate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(this.birthdate, formatter);
  }
  
}
