/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.Address;
import Model.City;
import Model.State;
import Model.Customer;
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
public class CustomerDAO {
    
   
    /**
     * 
     * @param customer 
     * Add a new customer to the customer table in the DB
     */
    public static void addCustomer(Customer customer){
        String customerQuery = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)" + 
                                "VALUES(?,(SELECT addressId FROM address WHERE address.addressId = ?), ?, now(), ?, now(), ?)"; 
        
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(customerQuery);
        
            stmt.setString(1, customer.getCustomerName());
            stmt.setInt(2, customer.getAddress().getAddressId());
            stmt.setInt(3, customer.getActive());
            stmt.setString(4, UserDAO.currentUser.getUserName());
            stmt.setString(5, UserDAO.currentUser.getUserName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    /**
     * 
     * @param customer 
     * Update an existing customer in the customer table 
     */
    public static void updatCustomer(Customer customer) {
        String sqlQuery = "UPDATE customer "
                        + "SET customerName = ?, addressId = ?, lastUpdate = now(), lastUpdateBy = ? "
                        + "WHERE customerId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, customer.getCustomerName());
            stmt.setInt(2, customer.getAddress().getAddressId());
            stmt.setString(3, UserDAO.currentUser.getUserName());
            stmt.setInt(4, customer.getCustomerId());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    /**
     * 
     * @return a list of all customers in the customer table
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        
        try {
            String getCustomers = "SELECT customerId, customerName, customer.addressId, active, address, address2, address.cityId, postalCode, phone, city, city.stateId, state  FROM customer " +
                                  "INNER JOIN address ON customer.addressID = address.addressId " +
                                  "INNER JOIN city ON address.cityId = city.cityId " +
                                  "INNER JOIN state ON city.stateId = state.stateId;";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getCustomers);
            
            
            while(rs.next()) {

                int id = (rs.getInt("customerId"));
                String name = (rs.getString("customerName"));
                int active = (rs.getInt("active"));
                int addressId = (rs.getInt("addressId"));
                String addressOne = (rs.getString("address"));
                String addressTwo = (rs.getString("address2"));
                String postal = (rs.getString("postalCode"));
                String phoneNum = (rs.getString("phone"));
                String tempCity = (rs.getString("city"));
                int cityId = (rs.getInt("cityId"));
                State state = new State();
                state.setStateId(rs.getInt("stateId"));
                state.setState(rs.getString("state"));
                
                
                City city = new City(cityId, tempCity, state);
                Address address = new Address(addressId, addressOne, addressTwo, city, postal, phoneNum);
                Customer customer = new Customer(id, name, address, active);
                allCustomers.add(customer);
               
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            }

        return allCustomers;
    }

    /**
     * 
     * @param customer 
     * Delete selected customer from the customer table
     */
    public static void deleteCustomer(Customer customer) {
        String sqlDelete = "DELETE FROM customer WHERE customerId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlDelete);
            stmt.setInt(1, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        //Delete customer address
        AddressDAO.deleteAddress(customer.getAddress());
        CityDAO.deleteCity(customer.getAddress().getCity());
        StateDAO.deleteState(customer.getAddress().getCity().getState());
    }
    
    public static ObservableList<Customer> getCustomerByName(String name) {
        ObservableList<Customer> searchedCustomer = FXCollections.observableArrayList();
        
        String query = "SELECT customerId, customerName, customer.addressId, active, address, address2, address.cityId, postalCode, phone, city, city.stateId, state  FROM customer " +
                       "INNER JOIN address ON customer.addressID = address.addressId " +
                       "INNER JOIN city ON address.cityId = city.cityId " +
                       "INNER JOIN state ON city.stateId = state.stateId " +
                       "WHERE customerName LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
                       while(rs.next()) {
                 int id = (rs.getInt("customerId"));
                String cName = (rs.getString("customerName"));
                int active = (rs.getInt("active"));
                int addressId = (rs.getInt("addressId"));
                String addressOne = (rs.getString("address"));
                String addressTwo = (rs.getString("address2"));
                String postal = (rs.getString("postalCode"));
                String phoneNum = (rs.getString("phone"));
                String tempCity = (rs.getString("city"));
                int cityId = (rs.getInt("cityId"));
                State state = new State();
                state.setStateId(rs.getInt("stateId"));
                state.setState(rs.getString("state"));
                
                
                City city = new City(cityId, tempCity, state);
                Address address = new Address(addressId, addressOne, addressTwo, city, postal, phoneNum);
                Customer customer = new Customer(id, cName, address, active);
                searchedCustomer.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchedCustomer;
    }
}
