/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.DBConnection;
import DAO.UserDAO;
import Model.Appointment;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button viewByMonthBtn;
    
    @FXML
    private Button viewByWeekBtn;
    
    @FXML
    private Button signOutBtn;

    @FXML
    private Button reportBtn;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private TableView<Customer> customerTv;

    @FXML
    private TableColumn<Customer, Integer> customerIdTvCol;

    @FXML
    private TableColumn<Customer, String> customerNameTvCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneTvCol;

    @FXML
    private TableColumn<Customer, String> customerAddTvCol;

    @FXML
    private TableColumn<Customer, String> customerAdd2TvCol;

    @FXML
    private TableColumn<Customer, String> customerPostalTvCol;

    @FXML
    private TableColumn<Customer, String> customerCityTvCol;

    @FXML
    private TableColumn<Customer, String> customerCountryTvCol;

    @FXML
    private TableColumn<Customer, Integer> customerActiveTvCol;

    @FXML
    private TableView<Appointment> appointmentsTv;

    @FXML
    private Button appointmentAddBtn;

    @FXML
    private Button appointmentUpdateBtn;

    @FXML
    private Button appointmentDeleteBtn;
    
    @FXML
    private TableColumn<Appointment, String> appCustomerNameCol;

    @FXML
    private TableColumn<Appointment, String> appConsultCol;

    @FXML
    private TableColumn<Appointment, String> appTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appStartCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appEndCol;
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the view by month scene
     */
    @FXML
    public void viewByMonthHandler(ActionEvent event) throws IOException{
        changeScene(event, "AppointmentMonth.fxml");
    }
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the view by week scene
     */
    @FXML
    public void viewByWeekHandler(ActionEvent event) throws IOException {
         changeScene(event, "AppointmentWeek.fxml");
    }
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the add appointment scene
     */
    @FXML
    public void addAppointmentHandler(ActionEvent event) throws IOException {
        
       changeScene(event, "AddAppointment.fxml");
    }
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the update appointment scene and send the into for the selected appointment
     */
    @FXML
    public void updateAppointmentHandler(ActionEvent event) throws IOException {
         Appointment app = appointmentsTv.getSelectionModel().getSelectedItem();
       if(app != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
            Parent modifyProductView = loader.load();
            Scene modifyProductScene = new Scene(modifyProductView);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();

        UpdateAppointmentController controller = loader.getController();

        controller.setAppointment(app);
       }
     
    }
    
    /**
     * 
     * @param event 
     * Deletes the selected appointment 
     */
    @FXML
    public void deleteAppointmentHandler(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to delete this appointment?");

        
            Appointment app =  appointmentsTv.getSelectionModel().getSelectedItem();

            if(app != null){
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    AppointmentDAO.deleteAppointment(app);
                    populateAppointmentTable();
            }
        }
        
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the reports scene
     */
    @FXML
    public void reportHandler(ActionEvent event) throws IOException {
        
       changeScene(event, "Reports.fxml");
    }
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the add customer scene
     */
    @FXML
    public void addCustomerHandler(ActionEvent event) throws IOException {
        
       changeScene(event, "AddCustomer.fxml");
    }
    
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Takes the user to the update customer scene and sends the information of the selected customer
     */
    @FXML
    public void updateCustomerHandler(ActionEvent event) throws IOException {
       Customer customer = customerTv.getSelectionModel().getSelectedItem();
       if(customer != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
            Parent modifyProductView = loader.load();
            Scene modifyProductScene = new Scene(modifyProductView);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();

        UpdateCustomerController controller = loader.getController();

        controller.setCustomer(customer);
       }
    }
    
    
    /**
     * 
     * @param event
     * @param scene
     * @throws IOException 
     * reusable code to change scene
     */
    void changeScene(ActionEvent event, String scene) throws IOException{
        Parent mainView = FXMLLoader.load(getClass().getResource(scene));
        Scene addPartScene = new Scene(mainView);
        
        //Get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    /**
     * 
     * @param event 
     * Deletes the selected customer and any appointments associated with them
     */
    @FXML
    public void deleteCustomer(ActionEvent event){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Error");
         alert.setContentText("Deleting customer will also delete any associated appointments.\n Are you sure you want to continue?");
            
           
         Customer customer = customerTv.getSelectionModel().getSelectedItem();
         if(customer != null) {
            Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    try {
                         AppointmentDAO.getAppointments(customer).stream()
                                     .forEach(app -> AppointmentDAO.deleteAppointment(app));
                         
                         CustomerDAO.deleteCustomer(customer);
                         populateCustomerTable();
                         populateAppointmentTable();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                     }
                }
         }
    }
    
    /**
     * Populates the customer table with all customers
     */
    public void populateCustomerTable() {
     
        customerTv.setItems(CustomerDAO.getAllCustomers());

        customerIdTvCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameTvCol.setCellValueFactory(new PropertyValueFactory<>("customerName")); 
        customerActiveTvCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        customerPhoneTvCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getPhone()));
        customerAddTvCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getAddress()));
        customerAdd2TvCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getAddressTwo()));
        customerPostalTvCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getPostalCode()));
        customerCityTvCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getCity().getCity()));
        customerCountryTvCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getAddress().getCity().getCountry().getCountry()));
    }
    /**
     * Populates the appointments table with all the appointments
     */
    public void populateAppointmentTable() {

        try {
            appointmentsTv.setItems(AppointmentDAO.getAppointments());
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        appCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appConsultCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>( "end"));

    }
    
    /**
     * 
     * @param event
     * @throws IOException
     * Closes the connection to the database and sets the current user info to null
     */
    @FXML
    void signOutHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to sign out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                DBConnection.closeConnection();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            UserDAO.currentUser.setUserName(null);
            changeScene(event, "Login.fxml");
        }
    }

    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        populateCustomerTable();
        populateAppointmentTable();
    }
}
