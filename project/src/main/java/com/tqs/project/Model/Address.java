package com.tqs.project.Model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.tqs.project.Exception.BadLocationException;

@Embeddable
public class Address {
    
    @NotNull(message = "latitude é obrigatório")
    private double latitude;
    @NotNull(message = "longitude é obrigatório")
    private double longitude;
    
    public Address() {
    }
    public Address(double latitude, double longitude) throws BadLocationException {
        if (isValid(latitude, longitude)){
            this.latitude = latitude;
            this.longitude = longitude;
        }
        else{
                    throw new BadLocationException("A latitude ou a longitude não se encontram nos valores possiveis, latitude deve estar entre -90 a 90 e longitude -180 a 180");
        }
        
    }
    @Override
    public String toString() {
        return "Address [latitude=" + latitude + ", longitude=" + longitude + "]";
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) throws BadLocationException {
        if (this.isValid(latitude, 0)){
            this.latitude = latitude;
        }
        else{
            throw new BadLocationException("A latitude ou a longitude não se encontram nos valores possiveis, latitude deve ser de -90 a 90");
        }
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) throws BadLocationException {
        if (this.isValid(0, longitude)){
            this.longitude = longitude;
        }
        else{
            throw new BadLocationException("A latitude ou a longitude não se encontram nos valores possiveis,  longitude -180 a 180");
        }
    }
    private boolean isValid(double latitude, double longitude){
        System.out.println(latitude+" "+longitude);
        if (latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude > 180.0){
            return false;
        }
        return true;
    }
    public double getDistance(Address x){
        //metodo a ver depois o que tem de fazer
        return 0;
    }
}
