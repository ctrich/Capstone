/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class Appointment {
    
    private int appointmentId;
    private int customerId;
    private String customerName;
    private String userName;
    private int userId;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int dentistId;
    private String dentistName;

    public Appointment(int appointmentId, int customerId,String customerName, String userName, int userId, String type, LocalDateTime start, LocalDateTime end,int dentistId, String dentistName) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.userName = userName;
        this.userId = userId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.dentistId = dentistId;
        this.dentistName = dentistName;
    }

    public Appointment() {
    }
    //appointment setters and getters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    
    public String getCustomerName(){
        return customerName;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }
    
    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    
    
}
