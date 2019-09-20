/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
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
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class CountryDAO {
    
    /**
     * 
     * @return a list of all countries in the country table
     */
    public static ObservableList<Country> getCountries(){
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String sqlQuery = "SELECT * FROM country";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next()) {
                Country country = new Country();
                country.setCountryId(rs.getInt("countryId"));
                country.setCountry(rs.getString("country"));
                countries.add(country);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return countries;
    }
    
    /**
     * 
     * @param name
     * @return all country information that belongs to the selected country name
     */
    public static Country getCountryByName(String name){
        Country country = new Country();
        String sqlQuery = "SELECT * FROM country WHERE country = ?";
        
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                
                country.setCountry(rs.getString("country"));
                country.setCountryId(rs.getInt("countryId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return country;
    }
    
  
    
}
