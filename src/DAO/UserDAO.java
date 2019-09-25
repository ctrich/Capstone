/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.User;
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
 * Class: C195
 */
public class UserDAO {
      
public static User currentUser = new User();
    
  /**
   * 
   * @param userName
   * @param password
   * @return 
   * Attempts to log in the user based on the info they entered
   */
    public static User login(String userName, String password){
        String userQuery = "SELECT * FROM user WHERE userName = ? AND password = ?";
        
        try{
            
            PreparedStatement stmt = conn.prepareStatement(userQuery);
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();

            while(result.next()) {
                
                 currentUser.setUserId(result.getInt("userId"));
                 currentUser.setUserName(result.getString("userName"));
                 currentUser.setPassword(result.getString("password"));
                 currentUser.setActive(result.getInt("active"));
            }
        
            }catch(SQLException e){
                System.out.println("Error: " + e);
        }
  
        return currentUser;
    }
    /**
     * 
     * @return all usernames
     * 
     */
    public static ObservableList<String> getAllUsersNames() {
        ObservableList<String> users = FXCollections.observableArrayList();
        String sqlQuery = "SELECT userName from user";
        
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next()) {
                String user = rs.getString("userName");
                
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
        
    }
    
    public static void addUser(String username, String password) {
        String insert = "INSERT INTO user(userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
                      + "VALUES(?, ?, 1, now(), 'admin', now(), 'admin')";
        
        PreparedStatement stmt;
    try {
        stmt = conn.prepareStatement(insert);
        stmt.setString(1, username);
        stmt.setString(2, password);
        
        stmt.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
}
