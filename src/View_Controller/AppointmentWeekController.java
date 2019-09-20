/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class AppointmentWeekController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<Appointment> appWeekTv;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private TableColumn<Appointment, String> customerCol;

    @FXML
    private TableColumn<Appointment, String> consultantCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Returns to the main scene
     */
    @FXML
    void backHandler(ActionEvent event) throws IOException {
        Parent mainView = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(mainView);
        
        //Get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }


    /**
     * 
     * @param url
     * @param rb 
     * Shows all appointments in the upcoming week
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            appWeekTv.setItems(AppointmentDAO.getAppointments(LocalDateTime.now()));
            
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            consultantCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }    
    
}
