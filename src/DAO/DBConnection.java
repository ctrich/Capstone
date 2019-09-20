/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class DBConnection {
    
    private static final String dBName = "U05zGF";
    private static final String dB_URL = "jdbc:mysql://52.206.157.109/" + dBName;
    private static final String userName = "U05zGF";
    private static final String password = "53688653204";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    
    public static void makeConnection() throws ClassNotFoundException, SQLException {
        
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(dB_URL, userName, password);
      
    }
    
    public static void closeConnection() throws SQLException {
        
        conn.close();
       
    }
    
}
