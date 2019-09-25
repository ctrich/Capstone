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
    
    public static void validateCity(String city) throws InputException{
        error.clear();
        if(!city.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" )){
            error.add("City can only contain lettters");
            throw new InputException("City can only contain letters");
        }
    }

     /**
     * 
     * @param name
     * @param address
     * @param address2
     * @param state
     * @param city
     * @param postal
     * @param phone
     * @throws InputException if any fields are blank
     */
    public static void checkForEmptyFields(String name, String address, String address2, String state, String city, String postal, String phone) throws InputException{
        
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
        
        if(state.isEmpty()){
            error.add("Please enter a state");
        }
        
        if(city.isEmpty()){
            error.add("Please enter a city");
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
     
    public static void validateState(String state)throws InputException {
        error.clear();
        
        if (!state.matches("AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|GA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|MA|MI|MN|MS|MO|MT" +
                           "|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|UT|VT|VI|VA|WA|WV|WI|WY")) {
            error.add("Enter a valid two letter abbreviation for state");
            throw new InputException("Enter a valid teo letter abbreviation for state");
        }
    }
    
    public static void validateZip(String zip)throws InputException {
        error.clear();
        if (!zip.matches("\\b\\d{5}(?:-\\d{4})?\\b")){
            error.add("Enter a five digit postal code");
            throw new InputException("Enter a five digit postal code");
        }
    }
    
}
