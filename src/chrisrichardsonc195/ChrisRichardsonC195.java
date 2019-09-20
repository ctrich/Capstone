/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chrisrichardsonc195;

import DAO.DBConnection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class ChrisRichardsonC195 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        
        
        
        launch(args);
        try {
            //close the connection to the database if the application is closed
            DBConnection.closeConnection();
        } catch (SQLException ex) {
             System.out.println(ex);
        }
    }
    
}
