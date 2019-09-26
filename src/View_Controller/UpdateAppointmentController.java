/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AppointmentDAO;
import DAO.DentistDAO;
import Exception.InputException;
import Exception.OverlapAppException;
import Model.Appointment;
import Validation.Validation;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class UpdateAppointmentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Appointment selectedApp;
    
    @FXML
    private TextField customerTxt;
    
    @FXML
    private ComboBox<String> dentistCB;

    @FXML
    private DatePicker dateDP;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<String> typeCB;
    
    @FXML
    private ComboBox<String> startTimeCB;

    @FXML
    private ComboBox<String> endTimeCB;
    
    private ObservableList<String> error = FXCollections.observableArrayList();
  
    /**
     * 
     * @param event
     * @throws IOException 
     * Saves the updated appointment information to the database
     */
    @FXML
    void saveUpdateHandler(ActionEvent event) throws IOException {
      
        
        LocalDate date = dateDP.getValue();
        String sHour = startTimeCB.getValue();
        String sMinute = startTimeCB.getValue();
        String eHour = endTimeCB.getValue();
        String eMinute = endTimeCB.getValue();
        String dentistName = dentistCB.getValue();
        
        //show an error message to the user if any fields are left blank
        try {
            Validation.checkAppForEmptyFields(date, sHour, eHour);
        } catch (InputException ex) {
            System.out.println(ex);
            String errors = Validation.error.stream().collect(Collectors.joining(" \n "));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }
        
        int sH = Integer.parseInt(sHour.substring(0, 2));
        int sM = Integer.parseInt(sMinute.substring(3));
        int eH = Integer.parseInt(eHour.substring(0, 2));
        int eM = Integer.parseInt(eMinute.substring(3));
        
        //show an error message if the start time is before the end time
        if(sH>eH){
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Start time must be before end time");
            alert.showAndWait();
            return;
        }else if((sH == eH) && (sM >= eM)){
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setContentText("Start time must be before end time");
                  alert.showAndWait();
                  return;
                 }
        
        LocalDateTime sLDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), sH, sM);
        LocalDateTime eLDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), eH, eM);
        
        //show and error message if the selected appointment times overlap any existing appointments
        try {
            Validation.checkUpdateForOverlap(selectedApp, sLDateTime, eLDateTime, dentistName);
        } catch (OverlapAppException ex) {
            System.out.println(ex);          
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment overlap");
            alert.setContentText("Your appointment overlaps another, please select a different time.");
            alert.showAndWait();
            return;
        }

            selectedApp.setType(typeCB.getValue());
            selectedApp.setStart(sLDateTime);
            selectedApp.setEnd(eLDateTime);
            selectedApp.setDentistName(dentistName);
            selectedApp.setDentistId(DentistDAO.getDentistIdByName(dentistName));
            
            AppointmentDAO.updateAppointment(selectedApp);
            changeScene(event);
        
    }
    

/**
 * 
 * @param app 
 * Sets the appointment information from the appointment that was selected on the main screen
 */
    public void setAppointment(Appointment app) {
        customerTxt.setText(app.getCustomerName());
        typeCB.setValue(app.getType());
        dentistCB.setValue(app.getDentistName());
        startTimeCB.setValue(app.getStart().toString().substring(11));
        endTimeCB.setValue(app.getEnd().toString().substring(11));
        dateDP.setValue(app.getStart().toLocalDate());
        selectedApp = app;
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Returns to the main screen without saving the updated information
     */
    @FXML
    public void cancelHandler(ActionEvent event) throws IOException{        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            changeScene(event);
        }
    }
    /**
     * 
     * @param event
     * @throws IOException 
     * reusable code to return to the main screen
     */
    public void changeScene(ActionEvent event) throws IOException {
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
     * populate the comboboxes
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String hour;
        String minute;
        
        
        
        ObservableList<String> types = FXCollections.observableArrayList("Check-up", "Cleaning", "Tooth Extraction", "Root Canal", "Gum Care", "Counseling", "Dentures", "Tooth Pain");
        ObservableList<String> dentists = FXCollections.observableArrayList(DentistDAO.getAllDentists());
        ObservableList<String> startTimes = FXCollections.observableArrayList();
        ObservableList<String> endTimes = FXCollections.observableArrayList();

        //adds the different time to both start and end comboboxes
        for(int i=8; i<17; i++){
            if(i < 10){
                hour = "0" + Integer.toString(i);
            }else hour = Integer.toString(i);
            
            for(int j=0; j<46; j+=15){
                if(j==0){
                   
                    minute = hour + ":" + Integer.toString(j) + "0";
                    startTimes.add(minute);
                    endTimes.add(minute);
                } else{
                    minute = hour + ":" + Integer.toString(j);
                    startTimes.add(minute); 
                    endTimes.add(minute);
                }
            }
        }
        endTimes.remove(0);
        endTimes.add("17:00");
        
          //disable past dates
        dateDP.setDayCellFactory(picker -> {
            return new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };  });
        
        typeCB.setItems(types);
        startTimeCB.setItems(startTimes);
        endTimeCB.setItems(endTimes);
        dentistCB.setItems(dentists);
    }    
    
}
