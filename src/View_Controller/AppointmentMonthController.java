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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class AppointmentMonthController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<Appointment> appTV;
    
    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<Appointment, String> customerCol;

    @FXML
    private TableColumn<Appointment, String> consultantCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private ComboBox<String> appMonthCB;
    
    /**
     * 
     * @param event 
     * Populate the Appointments table with the appointments for the selected month
     */
    @FXML
    void selectMonthHandler(ActionEvent event) {
        if(appMonthCB.getValue() != null){
            int month = 0;
               //assign a value to each month that can be used in the sql query to find the appointments for that month 
            switch(appMonthCB.getValue()) {
                case "January": month = 1;
                break;
                case "February": month = 2;
                break;
                case "March": month = 3;
                break;
                case "April": month = 4;
                break;
                case "May": month = 5;
                break;
                case "June": month = 6;
                break;
                case "July": month = 7;
                break;
                case "August": month = 8;
                break;
                case "September": month = 9;
                break;
                case "October": month = 10;
                break;
                case "November": month = 11;
                break;
                case "December": month = 12;
                break;
                default: break;
        }
            try {
                appTV.setItems(AppointmentDAO.getAppointments(month));
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            consultantCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        }
    }
    /**
     * 
     * @param event
     * @throws IOException 
     * Returns to the main scene
     */
    public void backHandler(ActionEvent event) throws IOException{
        Parent mainView = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(mainView);
        
        //Get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populate the month combobox
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        appMonthCB.setItems(months);
        
    }    
    
}
