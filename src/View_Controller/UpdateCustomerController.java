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
import Exception.InputException;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
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
public class UpdateCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField nameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField address2Txt;

    @FXML
    private ComboBox<String> cityCB;

    @FXML
    private ComboBox<String> countryCB;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

   private Customer selectedCustomer = new Customer(); 
   private ObservableList<String> error = FXCollections.observableArrayList();
    
   
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
        String selectedCountry = countryCB.getValue();
        String selectedCity = cityCB.getValue();
        String enteredPostal = postalTxt.getText();
        String enteredPhone = phoneTxt.getText();
       
        //Show an error to the user if they left any of the customer fields blank
        try {
            checkForEmptyFields(enteredName, enteredAddress, enteredAddress2, selectedCountry, selectedCity, enteredPostal, enteredPhone);
        } catch (InputException ex) {
            System.out.println(ex);
            String errors = error.stream().collect(Collectors.joining(" \n "));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }
        //Show a message if the phone field isn't only numbers
        try {
            phoneTxtIsNumbers(enteredPhone);
        } catch (InputException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(error.get(0));
            alert.showAndWait();
            return;
        }

        City city = CityDAO.getCityByName(selectedCity);
        //send the updated info to the database
        Address address = new Address(selectedCustomer.getAddress().getAddressId(), enteredAddress, enteredAddress2, city, enteredPostal, enteredPhone);
        AddressDAO.updateAddress(address);
        Customer customer = new Customer(selectedCustomer.getCustomerId(), nameTxt.getText(), address, 1);
        CustomerDAO.updatCustomer(customer);
        
        changeScene(event, "MainScreen.fxml");
    }
       /**
     * 
     * @param phone
     * @throws InputException if the phone field is not numbers only
     */
    public void phoneTxtIsNumbers(String phone) throws InputException{
        error.clear();
         if (phone.matches("[0-9]+") && phone.length() > 2){
             return;
         }
         error.add("The phone field should only contain numbers.");
         throw new InputException("Phone field should only contain numbers");
    }
    
    /**
     * 
     * @param name
     * @param address
     * @param address2
     * @param country
     * @param city
     * @param postal
     * @param phone
     * @throws InputException if any fields are blank
     * 
     */
    public void checkForEmptyFields(String name, String address, String address2, String country, String city, String postal, String phone) throws InputException{
        
        error.clear();
        
        if(name.isEmpty()){
            error.add("Please enter your name");
        }
      
        if(address.isEmpty()){
            error.add("Please enter an address");
        }
        
        if(address2.isEmpty()){
            error.add("If you do not have an address line 2 enter N/A");
        }
        
        if(country == null){
            error.add("Please select a country");
        }
        
        if(city == null){
            error.add("Please select a city");
        }
        
        if(postal.isEmpty()){
            error.add("Please enter a Postal code");
        }
        
        if(phone.isEmpty()){
            error.add("Please enter a Phone number");
        }
        if(!error.isEmpty()){
            throw new InputException("All fields must be filled in.");  
        }
            
    }
    
    /**
     * 
     * @param event 
     * Populates the cities in the Combobox based on the selected country
     */
    @FXML
    void populateCitiesHandler(ActionEvent event) {
        Country selectedCountry = CountryDAO.getCountryByName(countryCB.getValue());
       
        //Use a lambda expression to get the city names from the ObservableList. Using a lamdbda allowed me to get the city names without having to create more lists and use a for loop
        cityCB.setItems(CityDAO.getCitiesByCountry(selectedCountry).stream()
                                                                   .map(city -> city.getCity())
                                                                   .collect(Collectors.toCollection(FXCollections::observableArrayList)));

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
        countryCB.setValue(customer.getAddress().getCity().getCountry().getCountry());
        cityCB.setValue(customer.getAddress().getCity().getCity());
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
        
        //Populate the country Combobox using a lambda expression to get the country names
       countryCB.setItems(CountryDAO.getCountries().stream()
                                                   .map(country -> country.getCountry())
                                                   .collect(Collectors.toCollection(FXCollections::observableArrayList)));  
       
       
    }    
    
}
