/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris
 */
public class UserDAOTest {
    
    public UserDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of login method, of class UserDAO.
     */
    
    public String testLogin(String userName, String password) throws ClassNotFoundException, SQLException {
        DBConnection.makeConnection();
        
        String result = null;
        result = UserDAO.login(userName, password).getUserName();
       
        return result;
    }
    
    //Both the username and password are wrong
    @Test
    public void invalidUserAndPassword() throws ClassNotFoundException, SQLException {
        System.out.println("Invalid User");
        String userName = "fakeName";
        String password = "fakePassword";
        
        String failedResult = testLogin(userName, password);
        assertEquals(null, failedResult);
    }
    //The username is off by one letter
    @Test
    public void invalidUserName() throws ClassNotFoundException, SQLException {
        System.out.println("Invalid Username");
        String userName = "testt";
        String password = "test";
        
        String result = testLogin(userName, password);
        assertEquals(null, result);
    }
    //valid username and password
    @Test
    public void validUser() throws ClassNotFoundException, SQLException {
        System.out.println("Valid User");
        String userName = "test";
        String password = "test";
        
        String result = testLogin(userName, password);
        assertEquals("test", result);
    }
    //Password is off by one letter
    @Test
    public void invalidPassword() throws ClassNotFoundException, SQLException {
        System.out.println("Invalid Password");
        String userName = "test";
        String password = "testt";
        
        String result = testLogin(userName, password);
        assertEquals(null, result);
    }
}
