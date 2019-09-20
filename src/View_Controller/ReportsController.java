/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AppointmentDAO;
import DAO.CountryDAO;
import DAO.ReportsDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Country;
import Model.ReportAppByType;
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
public class ReportsController implements Initializable  {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Country> countryTv;
    
    @FXML 
    private TableColumn<Country, String> countryCol;
    
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
        
        consultCB.setItems(UserDAO.getAllUsersNames());
        
        countryTv.setItems(CountryDAO.getCountries());
        
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        
        

                
    }    
    
}
