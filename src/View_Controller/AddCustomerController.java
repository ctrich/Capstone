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
import Validation.Validation;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class AddCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
     
    @FXML
    private TextField nameTxt;
            
    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;
    
    @FXML
    private TextField addressTxt;

    @FXML
    private TextField address2Txt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;
    
    @FXML
    private TextField cityTxt;

    @FXML
    private TextField stateTxt;
    
    /**
     * 
     * @param event
     * @throws IOException 
     * Return to the main scene without saving customer info
     */
    @FXML
    public void cancelHandler(ActionEvent event) throws IOException{     
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
     * @throws IOException 
     * Save the customer info to the database
     */
    @FXML
    public void saveCustomerHandler(ActionEvent event) throws IOException {
   
        String enteredName = nameTxt.getText(); 
        String enteredAddress = addressTxt.getText();
        String enteredAddress2 = address2Txt.getText();
        String enteredState = stateTxt.getText().toUpperCase();
        String enteredCity = cityTxt.getText();
        String enteredPostal = postalTxt.getText();
        String enteredPhone = phoneTxt.getText();
        
        //Show an error message if there are any blank fields and validate info
        try {
            Validation.checkForEmptyFields(enteredName, enteredAddress, enteredAddress2, enteredState, enteredCity, enteredPostal, enteredPhone);
            Validation.validateName(enteredName);
            Validation.validateState(enteredState);
            Validation.validateZip(enteredPostal);
            Validation.validateCity(enteredCity);
        } catch (InputException ex) {
            System.out.println(ex);
            String errors = Validation.error.stream().collect(Collectors.joining(" \n "));
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }
        
        //show an error message if the phone field doesnt only contain numbers
        try {
            Validation.phoneTxtIsNumbers(enteredPhone);
        } catch (InputException ex) {
            System.out.println(ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(Validation.error.get(0));
            alert.showAndWait();
            return;
        }
       //save a new state to the database
        State state = new State();
        state.setState(enteredState);
        StateDAO.addState(state);
        //save a new city to the database
        City city = new City();
        city.setCity(enteredCity);
        city.setState(StateDAO.getStateByName(enteredState));
        CityDAO.addCity(city);
        //save the address and other customer information to the database
        Address address = new Address(enteredAddress, enteredAddress2, CityDAO.getCityByName(enteredCity), enteredPostal, enteredPhone);
        AddressDAO.addAddress(address);
        address.setAddressId(AddressDAO.getAddressId(address.getAddress(), address.getPostalCode()));
        Customer customer = new Customer(enteredName, address, 1);
        CustomerDAO.addCustomer(customer);
        //return to the main screen
        changeScene(event, "MainScreen.fxml");
        
        
    }

    
    /**
     * 
     * @param event
     * @param scene
     * @throws IOException 
     * Reusable code to change Scenes
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
