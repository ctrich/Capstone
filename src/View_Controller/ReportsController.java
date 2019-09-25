/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AppointmentDAO;
import DAO.DentistDAO;
import DAO.ReportsDAO;
import Model.Appointment;
import Model.ReportAppByType;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class ReportsController implements Initializable  {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Appointment> appTV;
    
    @FXML
    private TableColumn<Appointment, String> patientCol;
    
    @FXML
    private TableColumn<Appointment, String> consultantCol;
    
    @FXML
    private TableColumn<Appointment, String> typeCol1;
    
    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol1;
    
    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol1;
    
    @FXML
    private ComboBox<String> appMonthCB;
    
    @FXML
    private TableView<ReportAppByType> appTypeTv;

    @FXML
    private TableColumn<ReportAppByType, String> typeCol;

    @FXML
    private TableColumn<ReportAppByType, Integer> numberCol;

    @FXML
    private TableColumn<ReportAppByType, String> monthCol;
    
    @FXML
    private TableView<Appointment> scheduleTv;

    @FXML
    private TableColumn<Appointment, String> customerNameCol;
    
    @FXML
    private TableColumn<Appointment, String> sTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private ComboBox<String> consultCB;

    
    
    /**
     * 
     * @param event 
     * Populates the tableView with the appointments of the selected consultant
     */
    @FXML
    void getSchedule(ActionEvent event) {
        
        try {
            scheduleTv.setItems(AppointmentDAO.getAppointments(consultCB.getValue()));
        } catch (SQLException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        sTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>( "end"));
    }
    
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

            patientCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            consultantCol.setCellValueFactory(new PropertyValueFactory<>("dentistName"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol1.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol1.setCellValueFactory(new PropertyValueFactory<>("end"));
        }
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Returns to the main screen 
     */
    @FXML
    public void cancelHandler(ActionEvent event) throws IOException{        
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
     * Populates the tableview to show the number of appointment types by month
     * and the countries the company dies business in
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appTypeTv.setItems(ReportsDAO.getTypeByMonth());
        
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        consultCB.setItems(DentistDAO.getAllDentists());
        
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        appMonthCB.setItems(months);       
    }    
    
}
