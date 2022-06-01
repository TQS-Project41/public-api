package com.tqs.project.Model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.tqs.project.Exception.BadPhoneNumberException;

@Embeddable
public class DeliveryContact {
    
     @NotNull(message = "name é obrigatório")
    private String name;
    @NotNull(message = "phoneNumber é obrigatório")
    private String phoneNumber;

    public DeliveryContact() {
        }
    public DeliveryContact(String name, String phoneNumber) throws BadPhoneNumberException{
        if (this.isValid(phoneNumber)){
            this.name=name;
            this.phoneNumber=phoneNumber;
        }
        else{
            throw new BadPhoneNumberException("The phone number needs to have 9 digit"); 
        }

    }

    



    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) throws BadPhoneNumberException {
        if (this.isValid(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }
        else{
            throw new BadPhoneNumberException("The phone number needs to have 9 digit"); 
        }
    }


    @Override
    public String toString() {
        return "DeliveryContact [name=" + name + ", phoneNumber=" + phoneNumber + "]";
    }


    public boolean isValid(String number){
        if (number.matches("[0-9]+") && number.length() ==9) {
            return true;
        }
        return false;
    }
    

}
