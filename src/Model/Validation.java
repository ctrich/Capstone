/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Exception.InputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author chris
 */
public class Validation {
    public static ObservableList<String> error = FXCollections.observableArrayList();
    
    /**
     * 
     * @param name
     * @throws InputException 
     * Checks if the name is only letters
     */
    public static void validateName(String name) throws InputException{
        error.clear();
     if (!name.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" )) {
         error.add("The name field can only contain letters");
         throw new InputException("The name field can only contain letters");
     }
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
     */
    public static void checkForEmptyFields(String name, String address, String address2, String country, String city, String postal, String phone) throws InputException{
        
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
     * @param phone
     * @throws InputException if the phone field is not numbers only
     */
//    public void
     public static void phoneTxtIsNumbers(String phone) throws InputException{
        error.clear();
         if (phone.matches("[0-9]+") && phone.length() > 2){
             return;
         }
         error.add("The phone field should only contain numbers.");
         throw new InputException("Phone field should only contain numbers");
    }
     
     public static void validateAddress(String address)throws InputException{
         error.clear();
       if(!address.matches("\\d+[ ](?:[A-Za-z0-9.-]+[ ](?:[A-Za-z0-9.-])")) {
           error.add("Invalid address");
           throw new InputException("Invalid address");
       }
   } 
    
}
