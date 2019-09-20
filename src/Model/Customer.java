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
public class Customer {
    
    private int customerId;
    private String customerName;
    private Address address;
    private int active;

    public Customer(int customerId, String customerName, Address address, int active) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.active = active;
    }
    
    public Customer(String customerName, Address address, int active) {
        this.customerName = customerName;
        this.address = address;
        this.active = active;
    }

    public Customer() {
     
    }
    
    //customer setters and getters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
    
}
