/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.ReportAppByType;
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
public class ReportsDAO {
    
    /**
     * 
     * @return number of appointment types by moth 
     * 
     */
    public static ObservableList<ReportAppByType> getTypeByMonth(){
        ObservableList<ReportAppByType> appTypes = FXCollections.observableArrayList();
        
        String sqlQuery = "SELECT type, count(type), DATE_FORMAT(start, '%c') " +
                          "FROM appointment " +
                          "GROUP BY DATE_FORMAT(start, '%c'), type ";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next()) {
               ReportAppByType rp= new ReportAppByType();
               String month;
               int m = rs.getInt("DATE_FORMAT(start, '%c')");
               
               switch(m){
                   case 1: month = "January";
                   break;
                   case 2: month = "February";
                   break;
                   case 3: month = "March";
                   break;
                   case 4: month = "April";
                   break;
                   case 5: month = "May";
                   break;
                   case 6: month = "June";
                   break;
                   case 7: month = "July";
                   break;
                   case 8: month = "August";
                   break;
                   case 9: month = "September";
                   break;
                   case 10: month = "October";
                   break;
                   case 11: month = "November";
                   break;
                   case 12: month = "December";
                   break;
                   default: month = "Invalid Month";
                   break;
               }
               rp.setType(rs.getString("type"));
               rp.setNumber(rs.getInt("count(type)"));
               rp.setDate(month);
               
               appTypes.add(rp);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return appTypes;
    }
    

}
