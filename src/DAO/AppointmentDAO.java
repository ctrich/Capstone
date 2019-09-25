/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.Appointment;
import Model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class AppointmentDAO {
    
    /**
     * 
     * @param app 
     * Add an appointment to the appointment table
     */
    public static void addAppointment(Appointment app) {
        
        Timestamp start = changeTimetoUTC(app.getStart());
        Timestamp end = changeTimetoUTC(app.getEnd());
        
        int dentistId = DentistDAO.getDentistIdByName(app.getDentistName());
        
        String sqlInsert = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy, dentistId)"
                         + "VALUES(?,?,'','', '', '', ?,'', ?, ?, now(), ?, ?, ?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setInt(1, app.getCustomerId());
            stmt.setInt(2, app.getUserId());
             stmt.setString(3, app.getType());
            stmt.setTimestamp(4, start);
            stmt.setTimestamp(5, end);
            stmt.setString(6, UserDAO.currentUser.getUserName());
            stmt.setString(7, UserDAO.currentUser.getUserName());
            stmt.setInt(8, dentistId);
   
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * 
     * @param app 
     * Delete the selected appointment from the appointment table
     */
    public static void deleteAppointment(Appointment app){
        String sqlDelete = "DELETE FROM appointment WHERE appointmentId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlDelete);
            stmt.setInt(1, app.getAppointmentId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    /**
     * 
     * @param app 
     * Update an existing appointment in the appointment table
     */
    public static void updateAppointment(Appointment app) {
        Timestamp start = changeTimetoUTC(app.getStart());
        Timestamp end = changeTimetoUTC(app.getEnd());
        String sqlUpdate = "UPDATE appointment SET userId = ?, type = ?, start = ?, end = ?, lastUpdateBy = ?, dentistId = ? "
                         + "WHERE appointmentId = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(1, app.getUserId());
            stmt.setString(2, app.getType());
            stmt.setTimestamp(3, start);
            stmt.setTimestamp(4, end);
            stmt.setString(5, UserDAO.currentUser.getUserName());
            stmt.setInt(6, app.getDentistId());
            stmt.setInt(7, app.getAppointmentId());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    /**
     * 
     * @param query
     * @param stmt
     * @param rs
     * @return a list of all appointments from the appointment table that meet the entered parameters
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(String query, PreparedStatement stmt, ResultSet rs) throws SQLException{
        ObservableList<Appointment> all = FXCollections.observableArrayList();
    
            while(rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("appointmentId"));
                app.setCustomerId(rs.getInt("customerId"));
                app.setCustomerName(rs.getString("customerName"));
                app.setUserId(rs.getInt("userId"));
                app.setUserName(rs.getString("userName"));
                app.setType(rs.getString("type"));
                app.setDentistId(rs.getInt("dentistId"));
                app.setDentistName(rs.getString("lastName"));
                
                
                
                String start = rs.getString("start");
                String end = rs.getString("end");
                String sDate = start.substring(0, 10);
                int sYear = Integer.parseInt(sDate.substring(0, 4));
                int sMonth = Integer.parseInt(sDate.substring(5, 7));
                int sDay = Integer.parseInt(sDate.substring(8, 10));
                int sHour = Integer.parseInt(start.substring(11, 13));
                int sMinute = Integer.parseInt(start.substring(14, 16));
               
                LocalDateTime sDT = LocalDateTime.of(sYear, sMonth, sDay, sHour, sMinute);
                //let the application know that the time is UTC time
                ZonedDateTime sUTC = ZonedDateTime.of(sDT, ZoneId.of("UTC"));
                //apply the offset of the system time zone
                ZonedDateTime sBackToLocal = sUTC.withZoneSameInstant(ZoneOffset.systemDefault());
                
                app.setStart(sBackToLocal.toLocalDateTime());
                
                String eDate = end.substring(0, 10);
                int eYear = Integer.parseInt(eDate.substring(0, 4));
                int eMonth = Integer.parseInt(eDate.substring(5, 7));
                int eDay = Integer.parseInt(eDate.substring(8, 10));
                int eHour = Integer.parseInt(end.substring(11, 13));
                int eMinute = Integer.parseInt(end.substring(14, 16));
                
                LocalDateTime eDT = LocalDateTime.of(eYear, eMonth, eDay, eHour, eMinute);
                ZonedDateTime eUTC = ZonedDateTime.of(eDT, ZoneId.of("UTC"));
                ZonedDateTime eBackToLocal = eUTC.withZoneSameInstant(ZoneOffset.systemDefault());
                
                app.setEnd(eBackToLocal.toLocalDateTime());
                
                all.add(app);
            }
       
        return all;
    }
    
    /**
     * Overloaded function
     * @return a list of all appointments from the appointment table
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException{
       String sqlQuery = "SELECT customer.customerName, customer.customerId, user.userId, user.userName, dentist.dentistId, dentist.lastName, appointmentId, appointment.type, appointment.start, appointment.end "
                        + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                        + "INNER JOIN user ON appointment.userId = user.userId "
                        + "INNER JOIN dentist ON appointment.dentistId = dentist.dentistId";
            PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = pStmt.executeQuery();
            
            return getAppointments(sqlQuery, pStmt, rs);
    }
    
    /**
     * Overloaded function
     * @param user
     * @return a list of all appointments for the entered user
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(String dentist) throws SQLException{
         String sqlQuery = "SELECT customer.customerName, customer.customerId,user.userId, user.userName, dentist.dentistId, dentist.lastName, appointmentId, appointment.type, appointment.start, appointment.end "
                        + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                        + "INNER JOIN user ON appointment.userId = user.userId "
                        + "INNER JOIN dentist ON appointment.dentistId = dentist.dentistId "
                        + "WHERE dentist.lastName = ?";
            PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
            pStmt.setString(1, dentist);
            ResultSet rs = pStmt.executeQuery();
            
            return getAppointments(sqlQuery, pStmt, rs);
    }
    
    
    /**
     * Overloaded function
     * @param month
     * @return a list of all appointments in the entered month
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(int month) throws SQLException{
                    String sqlQuery = "SELECT customer.customerName, customer.customerId, user.userId, user.userName, dentist.dentistId, dentist.lastName, appointmentId, appointment.type, appointment.start, appointment.end "
                                    + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                                    + "INNER JOIN user ON appointment.userId = user.userId "
                                    + "INNER JOIN dentist ON appointment.dentistId = dentist.dentistId "
                                    + "WHERE DATE_FORMAT(appointment.start, '%c') = ?";
        
            PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
            pStmt.setInt(1, month);
            ResultSet rs = pStmt.executeQuery(); 
            
            return getAppointments(sqlQuery, pStmt, rs);
    }
    /**
     * Overloaded function
     * @param ldt
     * @return a list of all appointments within a week of the entered date
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(LocalDateTime ldt) throws SQLException{
        //get a date and time a week from the entered date and time
        LocalDateTime week = ldt.plusDays(7);
        //change the date and time to UTC time
        Timestamp start = changeTimetoUTC(ldt);
        Timestamp end = changeTimetoUTC(week);
        
        String sqlQuery = "SELECT customer.customerName, customer.customerId, user.userId, user.userName, appointmentId, appointment.type, appointment.start, appointment.end "
                                    + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                                    + "INNER JOIN user ON appointment.userId = user.userId "
                                    + "WHERE start BETWEEN ? AND ?";
        PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
            pStmt.setTimestamp(1, start);
            pStmt.setTimestamp(2, end);
            ResultSet rs = pStmt.executeQuery(); 
            
            return getAppointments(sqlQuery, pStmt, rs);
    }
    /**
     * Overloaded function
     * @param customer
     * @return a list of appointments for the entered customer
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(Customer customer) throws SQLException{
        String sqlQuery = "SELECT customer.customerName, customer.customerId, user.userId, user.userName, dentist.dentistId, dentist.lastName, appointmentId, appointment.type, appointment.start, appointment.end "
                                    + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                                    + "INNER JOIN user ON appointment.userId = user.userId "
                                    + "INNER JOIN dentist ON appointment.dentistId = dentist.dentistId "
                                    + "WHERE appointment.customerId = ?";
        
        PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
            pStmt.setInt(1, customer.getCustomerId());
            ResultSet rs = pStmt.executeQuery(); 
            
            return getAppointments(sqlQuery, pStmt, rs);
    }
    /**
     * Overloaded function
     * @param start
     * @param end
     * @return a list of appointments that overlap with the entered times
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(LocalDateTime start, LocalDateTime end, String dentistName) throws SQLException {
        
        //change time to UTC
        Timestamp sTime = changeTimetoUTC(start);
        Timestamp eTime = changeTimetoUTC(end);
        System.out.println(dentistName);
        
        String sqlQuery =   "SELECT customer.customerName, customer.customerId, user.userId, user.userName, dentist.dentistId, dentist.lastName, appointmentId, appointment.type, appointment.start, appointment.end "
                          + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                          + "INNER JOIN user ON appointment.userId = user.userId "
                          + "INNER JOIN dentist ON appointment.dentistId = dentist.dentistId "
                          + "WHERE ((appointment.start >= ? AND appointment.end <= ?) "
                          + "OR (appointment.start <= ? AND appointment.end >= ?) "
                          + "OR (appointment.start BETWEEN ? AND ? OR appointment.end BETWEEN ? AND ?)) "
                          + "AND dentist.lastName = ?";
      
          PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
          pStmt.setTimestamp(1, sTime);
          pStmt.setTimestamp(2, eTime);
          pStmt.setTimestamp(3, sTime);
          pStmt.setTimestamp(4, eTime);
          pStmt.setTimestamp(5, sTime);
          pStmt.setTimestamp(6, eTime);
          pStmt.setTimestamp(7, sTime);
          pStmt.setTimestamp(8, eTime);
          pStmt.setString(9, dentistName);
          ResultSet rs = pStmt.executeQuery(); 
            
          return getAppointments(sqlQuery, pStmt, rs);
    }
    
    /**
     * Overloaded function
     * @param app
     * @param start
     * @param end
     * @return a list of appointments that overlap the entered times excluding the entered appointment
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointments(Appointment app, LocalDateTime start, LocalDateTime end, String dentistName) throws SQLException {
       
        
        Timestamp sTime = changeTimetoUTC(start);
        Timestamp eTime = changeTimetoUTC(end);
        
        String sqlQuery =   "SELECT customer.customerName, customer.customerId, user.userId, user.userName,dentist.dentistId, dentist.lastName, appointmentId, appointment.type, appointment.start, appointment.end "
                          + "FROM customer INNER JOIN appointment ON customer.customerId = appointment.customerId "
                          + "INNER JOIN user ON appointment.userId = user.userId "
                          + "INNER JOIN dentist ON appointment.dentistId = dentist.dentistId "
                          + "WHERE (appointment.start >= ? AND appointment.end <= ?) "
                          + "AND appointment.appointmentId != ? "
                          + "AND dentist.lastname = ? "
                          + "OR (appointment.start <= ? AND appointment.end >= ?) "
                          + "AND appointment.appointmentId != ? "
                          + "AND dentist.lastname = ? "
                          + "OR (appointment.start BETWEEN ? AND ? OR appointment.end BETWEEN ? AND ?) "
                          + "AND appointment.appointmentId != ? "
                          + "AND dentist.lastname = ?";
      
          PreparedStatement pStmt = conn.prepareStatement(sqlQuery);
          pStmt.setTimestamp(1, sTime);
          pStmt.setTimestamp(2, eTime);
          pStmt.setInt(3, app.getAppointmentId());
          pStmt.setString(4, dentistName);
          pStmt.setTimestamp(5, sTime);
          pStmt.setTimestamp(6, eTime);
          pStmt.setInt(7, app.getAppointmentId());
          pStmt.setString(8, dentistName);
          pStmt.setTimestamp(9, sTime);
          pStmt.setTimestamp(10, eTime);
          pStmt.setTimestamp(11, sTime);
          pStmt.setTimestamp(12, eTime);
          pStmt.setInt(13, app.getAppointmentId());
          pStmt.setString(14, dentistName);
          ResultSet rs = pStmt.executeQuery(); 
            
          return getAppointments(sqlQuery, pStmt, rs);
    }

    /**
     * 
     * @param loc
     * @return a timestamp of the datetime changed to UTC 
     */
    public static Timestamp changeTimetoUTC(LocalDateTime loc){
        ZonedDateTime LocZDateTime = ZonedDateTime.of(loc, ZoneId.systemDefault());
        ZonedDateTime UTCZdt = LocZDateTime.withZoneSameInstant(ZoneOffset.UTC);

        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp uTCTime = Timestamp.valueOf(customFormatter.format(UTCZdt));
        
        return uTCTime;
    }        
    
    

}
