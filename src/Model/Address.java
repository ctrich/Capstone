/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class Address {
    
    private int addressId;
    private String address;
    private String addressTwo;
    private City city;
    private String postalCode;
    private String phone;

    public Address(int addressId, String address, String addressTwo, City city, String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.addressTwo = addressTwo;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Address(String address, String addressTwo, City city, String postalCode, String phone) {
        this.address = address;
        this.addressTwo = addressTwo;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    public Address() {
    }

    //address setters and getters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
