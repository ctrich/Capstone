/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.State;
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
public class StateDAO {
    
    /**
     * 
     * @return a list of all states in the state table
     */
    public static ObservableList<State> getStates(){
        ObservableList<State> states = FXCollections.observableArrayList();
        String sqlQuery = "SELECT * FROM state";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next()) {
                State state = new State();
                state.setStateId(rs.getInt("stateId"));
                state.setState(rs.getString("state"));
                states.add(state);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return states;
    }
    
    /**
     * 
     * @param name
     * @return all state information that belongs to the selected state name
     */
    public static State getStateByName(String name){
        State state = new State();
        String sqlQuery = "SELECT * FROM state WHERE state = ?";
        
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                
                state.setState(rs.getString("state"));
                state.setStateId(rs.getInt("stateId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return state;
    }
    
    /**
     * 
     * @param state 
     * Add a state to the database when a new customer is saved
     */
    public static void addState(State state) {
     String insert = "INSERT INTO state (state, createDate, createdBy, lastUpdate, lastUpdateBy) "
                   + "VALUES (?, now(), ?, now(), ?)";
     
        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, state.getState());
            stmt.setString(2, UserDAO.currentUser.getUserName());
            stmt.setString(3, UserDAO.currentUser.getUserName());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param state 
     * delete a state from the database when a customer is deleted
     */
    public static void deleteState(State state) {
        String delete = "DELETE FROM state WHERE stateId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, state.getStateId());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param state 
     * Update the customers state information in the database
     */
    public static void updateState(State state) {
        String update = "UPDATE state SET state = ? WHERE stateId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, state.getState());
            stmt.setInt(2, state.getStateId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
