/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.City;
import Model.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class CityDAO {
    
    /**
     * 
     * @param state
     * @return a list of cities that are associated with the selected state
     */
    public static ObservableList<City> getCitiesBystate(State state){
        ObservableList<City> cities = FXCollections.observableArrayList();
        String sqlQuery = "SELECT city, cityId FROM city "
                        + "INNER JOIN state ON city.stateId = state.stateId "
                        + "WHERE state.stateId = " + state.getStateId();
        
        try {
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next()) {
                City city = new City();
                city.setCity(rs.getString("city"));
                city.setCityId(rs.getInt("cityId"));
                cities.add(city);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return cities;
    }
    
    /**
     * 
     * @param name
     * @return all city info that belongs to the selected city
     */
   public static City getCityByName(String name){
       City city = new City();
       String sqlQuery = "SELECT * FROM city WHERE city = ?";
       
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                city.setCity(rs.getString("city"));
                city.setCityId(rs.getInt("cityId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
       return city;
   }
    
   public static void addCity(City city) {
       String insert = "INSERT INTO city (city, stateId, createDate, createdBy, lastUpdate, lastUpdateBy) "
                     + "VALUES (?, ?, now(), ?, now(), ?)";
       
        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, city.getCity());
            stmt.setInt(2, city.getState().getStateId());
            stmt.setString(3, UserDAO.currentUser.getUserName());
            stmt.setString(4, UserDAO.currentUser.getUserName());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public static void deleteCity(City city) {
       String delete = "DELETE FROM city WHERE cityId = ?";
       
        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, city.getCityId());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public static void updateCity(City city) {
       String update = "UPDATE city SET city = ? WHERE cityId = ?";
       
        try {
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, city.getCity());
            stmt.setInt(2, city.getCityId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
