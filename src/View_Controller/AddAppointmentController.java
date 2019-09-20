/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Exception.InputException;
import Exception.OverlapAppException;
import Model.Appointment;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
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
public class AddAppointmentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<Customer> customerTv;
    
    @FXML
    private TableColumn<Customer, String> customerCol;
    
    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private DatePicker dateDP;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

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
    * Save appointment info to the database
    */
    @FXML
    void saveAppointmentHandler(ActionEvent event) throws IOException {
        //show an error message if a customer was not selected from the tableview
        try{
            String customerName = customerTv.getSelectionModel().getSelectedItem().getCustomerName();
            int customerId = customerTv.getSelectionModel().getSelectedItem().getCustomerId();
         }catch(NullPointerException e){
            System.out.println(e);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
            return;
        }
        
        String customerName = customerTv.getSelectionModel().getSelectedItem().getCustomerName();
        int customerId = customerTv.getSelectionModel().getSelectedItem().getCustomerId();

        LocalDate date = dateDP.getValue();
        String sHour = startTimeCB.getValue();
        String sMinute = startTimeCB.getValue();
        String eHour = endTimeCB.getValue();
        String eMinute = endTimeCB.getValue();
        String userName = UserDAO.currentUser.getUserName();
        int userId = UserDAO.currentUser.getUserId();
        String type = typeCB.getValue();
        
        //Show an error message if any of the fields were left blank
        try {
            checkForEmptyFields(date, sHour, eHour, type);
        } catch (InputException ex) {
            System.out.println(ex);
            System.out.println(ex);
            String errors = error.stream().collect(Collectors.joining(" \n "));
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }

        int sH = Integer.parseInt(sHour.substring(0, 2));
        int sM = Integer.parseInt(sMinute.substring(3));
        int eH = Integer.parseInt(eHour.substring(0, 2));
        int eM = Integer.parseInt(eMinute.substring(3));
        
        //Show an error message if the start time is the same or later than the end time
        if(sH>eH){
             Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Start time must be before end time");
            alert.showAndWait();
            return;
        }else if((sH == eH) && (sM >= eM)){
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setContentText("Start time must be before end time");
                  alert.showAndWait();
                  return;
                 }
    
        LocalDateTime sLDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), sH, sM);
        LocalDateTime eLDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), eH, eM);
        
        //Show an error message if the appointment times overlap with an existing appointment
        try {
            checkForOverlap(sLDateTime, eLDateTime);
             } catch (OverlapAppException ex) {
            System.out.println(ex);
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Appointment overlap");
            alert.setContentText("Your appointment overlaps another, please select a different time.");
            alert.showAndWait();
            return;
        }
        
            Appointment app = new Appointment();
            app.setCustomerName(customerName);
            app.setUserName(userName);
            app.setCustomerId(customerId);
            app.setUserId(userId);
            app.setType(type);
            app.setStart(sLDateTime);
            app.setEnd(eLDateTime);

            AppointmentDAO.addAppointment(app);
            changeScene(event);
   
       
    }
    
    /**
     * 
     * @param date
     * @param start
     * @param end
     * @param type
     * @throws InputException if any fields are blank
     */
    public void checkForEmptyFields(LocalDate date, String start, String end, String type) throws InputException{
        error.clear();
        
        if(date == null){
            error.add("Please select a date");
        }
        if(start == null){
            error.add("Please select a starting time");
        }
       
        if(end == null){
            error.add("Please select an ending time");
        }
       
        if(type == null){
            error.add("Please select an appointment type");
        }
        
        if(!error.isEmpty()){
            throw new InputException("Please make a selection for all fields");
        }
        
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Return to the main screen without saving appointment info
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
     * Reusable change scene code
     */
    public void changeScene(ActionEvent event) throws IOException{
        Parent mainView = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(mainView);
        
        //Get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    /**
     * 
     * @param start
     * @param end
     * @throws OverlapAppException if the appointment times overlap an existing appointment
     * 
     */
    public void checkForOverlap(LocalDateTime start, LocalDateTime end) throws OverlapAppException{
        try {
            if(AppointmentDAO.getAppointments(start, end).size() > 0)
                throw new OverlapAppException();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
     
    }
    
    /**
     * 
     * @param url
     * @param rb 
     * Populate the comboboxes and tableviews
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String hour;
        String minute;
        //disable past dates
        dateDP.setDayCellFactory(picker -> new DateCell() {
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();

            setDisable(empty || date.compareTo(today) < 0 );
        }
    });
        
        ObservableList<String> types = FXCollections.observableArrayList("First Meeting", "First Consult", "Update", "Consult");
        ObservableList<String> startTimes = FXCollections.observableArrayList();
        ObservableList<String> endTimes = FXCollections.observableArrayList();
        ObservableList<Customer> customers = FXCollections.observableArrayList(CustomerDAO.getAllCustomers());

        customers.sort((a, b) -> a.getCustomerName().compareTo(b.getCustomerName()));
        
        //Add the times for the start and end time comboboxes
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

        typeCB.setItems(types);
        startTimeCB.setItems(startTimes);
        endTimeCB.setItems(endTimes);
        customerTv.setItems(customers);
 
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getAddress()));
                                        
    }
             
    
}
