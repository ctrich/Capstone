/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.AddressDAO;
import DAO.CityDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import Exception.FormatException;
import Exception.InputException;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.Validation;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
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
    private ComboBox<String> cityCB;

    @FXML
    private ComboBox<String> countryCB;
    
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
     * Populate the cities combobox based on the selected country
     */
    @FXML
    void populateCitiesHandler(ActionEvent event) {
          Country selectedCountry = CountryDAO.getCountryByName(countryCB.getValue());

        //get the city names from a list
        cityCB.setItems(CityDAO.getCitiesByCountry(selectedCountry).stream()
                                                                   .map(city -> city.getCity())
                                                                   .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        
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
        String selectedCountry = countryCB.getValue();
        String selectedCity = cityCB.getValue();
        String enteredPostal = postalTxt.getText();
        String enteredPhone = phoneTxt.getText();
        
        //Show an error message if there are any blank fields
        try {
            Validation.checkForEmptyFields(enteredName, enteredAddress, enteredAddress2, selectedCountry, selectedCity, enteredPostal, enteredPhone);
            Validation.validateName(enteredName);
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
       
        Country country = CountryDAO.getCountryByName(selectedCountry);
        City city = CityDAO.getCityByName(selectedCity);
        city.setCountry(country);
        Address address = new Address(enteredAddress, enteredAddress2, city, enteredPostal, enteredPhone);
        AddressDAO.addAddress(address);
        address.setAddressId(AddressDAO.getAddressId(address.getAddress(), address.getPostalCode()));
        Customer customer = new Customer(enteredName, address, 1);
        CustomerDAO.addCustomer(customer);
            
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
                
       //Iterate through the list and return an observablelist of country names
        countryCB.setItems(CountryDAO.getCountries().stream()
                                                    .map(country -> country.getCountry())
                                                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));       

  
    }    
    
}
