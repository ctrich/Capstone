/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class User {
    
    private ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
    private int userId;
    private String userName;
    private String password;
    private int active;

    public User(int userId, String userName, String password, int active) {
        
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
    }

    public User() {
    }

    //User setters and getters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    public void addUserAppointments(Appointment appointment){
        this.userAppointments.add(appointment);
    }
    
    public void deleteUserAppointment(int index, Appointment selectedAppointment) {
        for(Appointment appointment : userAppointments) {
            if(appointment.getAppointmentId() == selectedAppointment.getAppointmentId())
                userAppointments.remove(appointment);
        }
    }
    
    public ObservableList<Appointment> getUserAppointments(){
        return userAppointments;
    }
    
    
    
}
