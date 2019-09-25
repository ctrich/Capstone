/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.Address;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class AddressDAO {
    
    /**
     * 
     * @param address 
     * Add an address to the address table in the database
     */
    public static void addAddress(Address address) {
        String sqlInsert = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                           "VALUES(?, ?, ?, ?, ?, now(), ?, now(), ?)";


        try {
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, address.getAddress());
            stmt.setString(2, address.getAddressTwo());
            stmt.setInt(3, address.getCity().getCityId());
            stmt.setString(4, address.getPostalCode());
            stmt.setString(5, address.getPhone());
            stmt.setString(6, UserDAO.currentUser.getUserName());
            stmt.setString(7, UserDAO.currentUser.getUserName());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * 
     * @param address 
     * Update an existing address in the address table of the database
     */
    public static void updateAddress(Address address){
        String sqlUpdate = "UPDATE address "
                         + "SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = now(), lastUpdateBy = ?"
                         + "WHERE addressId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, address.getAddress());
            stmt.setString(2, address.getAddressTwo());
            stmt.setInt(3, address.getCity().getCityId());
            stmt.setString(4, address.getPostalCode());
            stmt.setString(5, address.getPhone());
            stmt.setString(6, UserDAO.currentUser.getUserName());
            stmt.setInt(7, address.getAddressId());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        
    }

    /**
     * 
     * @param address 
     * Delete the selected address from the address table in the database
     */
    public static void deleteAddress(Address address){
        String sqlDelete = "DELETE FROM address WHERE addressId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlDelete);
            stmt.setInt(1, address.getAddressId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
                            
                        
    
    /**
     * 
     * @param address
     * @param postalCode
     * @return the address ID that belongs to the address and postal code in the address table
     */
    public static int getAddressId (String address, String postalCode){
        
            int id = 0;
            String sqlQuery = "SELECT addressId FROM address WHERE address = ? AND postalCode = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, address);
            stmt.setString(2, postalCode);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                id = rs.getInt("addressId");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return id;
    }
}
