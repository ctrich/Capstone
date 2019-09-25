/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class DentistDAO {
    
    /**
     * 
     * @return all dentists in the database in an observablelist
     */
    public static ObservableList<String> getAllDentists() {
        ObservableList<String> dentists = FXCollections.observableArrayList();
        try {
            String getDentists = "SELECT lastName from dentist";
            Statement stmt = conn.createStatement();
        
            ResultSet rs = stmt.executeQuery(getDentists);
            
            while(rs.next()) {
                dentists.add(rs.getString("lastName"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DentistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dentists;
    }
    
    /**
     * 
     * @param name
     * @return dentist
     * searches database by name
     */
    public static int getDentistIdByName(String name) {
        int dentistId = 0;
            String sqlStatement = "Select dentistId from dentist where lastName = ?";
        try {    
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                dentistId = rs.getInt("dentistId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DentistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dentistId;
    }
    
    /**
     * 
     * @param name 
     * saves a new dentist to the database
     */
    public static void insertDentist(String name) {
        String insert = "INSERT INTO dentist(lastName) VALUES(?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, name);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DentistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
