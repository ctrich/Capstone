/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AddressDAO;
import DAO.CityDAO;
import DAO.StateDAO;
import DAO.CustomerDAO;
import Exception.InputException;
import Model.Address;
import Model.City;
import Model.State;
import Model.Customer;
import Model.Validation;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class UpdateCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField nameTxt;
    
    @FXML
    private TextField stateTxt;
    
    @FXML
    private TextField cityTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField address2Txt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

   private Customer selectedCustomer = new Customer(); 
    
   
/**
 * 
 * @param event
 * @throws IOException 
 * Saves the updated customer information to the database
 */
    @FXML
    void saveUpdateHandler(ActionEvent event) throws IOException {
        
        String enteredName = nameTxt.getText();
        String enteredAddress = addressTxt.getText(); 
        String enteredAddress2 = address2Txt.getText();
        String selectedstate = stateTxt.getText().toUpperCase();
        String selectedCity = cityTxt.getText();
        String enteredPostal = postalTxt.getText();
        String enteredPhone = phoneTxt.getText();
       
        //Show an error to the user if they left any of the customer fields blank
        try {
            Validation.checkForEmptyFields(enteredName, enteredAddress, enteredAddress2, selectedstate, selectedCity, enteredPostal, enteredPhone);
            Validation.validateName(enteredName);
            Validation.validateState(selectedstate);
            Validation.validateZip(enteredPostal);
            Validation.validateCity(selectedCity);
        } catch (InputException ex) {
            System.out.println(ex);
            String errors = Validation.error.stream().collect(Collectors.joining(" \n "));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }
        //Show a message if the phone field isn't only numbers
        try {
            Validation.phoneTxtIsNumbers(enteredPhone);
        } catch (InputException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(Validation.error.get(0));
            alert.showAndWait();
            return;
        }
        State state = selectedCustomer.getAddress().getCity().getState();
        state.setState(selectedstate);
        City city = selectedCustomer.getAddress().getCity();
        city.setCity(selectedCity);
        CityDAO.updateCity(city);
        StateDAO.updateState(state);
        
        //send the updated info to the database
        Address address = new Address(selectedCustomer.getAddress().getAddressId(), enteredAddress, enteredAddress2, city, enteredPostal, enteredPhone);
        AddressDAO.updateAddress(address);
        Customer customer = new Customer(selectedCustomer.getCustomerId(), nameTxt.getText(), address, 1);
        CustomerDAO.updatCustomer(customer);
        
        changeScene(event, "MainScreen.fxml");
    }
    
    /**
     * 
     * @param customer 
     * Set all the customer info from the customer that was selected on the main screen
     */
    public void setCustomer(Customer customer){
        nameTxt.setText(customer.getCustomerName());
        addressTxt.setText(customer.getAddress().getAddress());
        address2Txt.setText(customer.getAddress().getAddressTwo());
        stateTxt.setText(customer.getAddress().getCity().getState().getState());
        cityTxt.setText(customer.getAddress().getCity().getCity());
        postalTxt.setText(customer.getAddress().getPostalCode());
        phoneTxt.setText(customer.getAddress().getPhone());
        selectedCustomer = customer;
    }
    
    
    /**
     * 
     * @param event
     * @throws IOException 
     * returns to the main screen without saving changes
     */
    @FXML 
    public void cancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            changeScene(event, "MainScreen.fxml");
        }
    }
    
    /**
     * 
     * @param event
     * @param scene
     * @throws IOException 
     * Changes scenes
     */
    public void changeScene(ActionEvent event, String scene) throws IOException{
       Parent mainView = FXMLLoader.load(getClass().getResource(scene));
       Scene addPartScene = new Scene(mainView);
        
        //Get the stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
