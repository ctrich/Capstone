/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;


import DAO.AppointmentDAO;
import DAO.DBConnection;
import DAO.UserDAO;
import Model.Appointment;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C868
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private Label errorlbl;
    
    @FXML
    private Label loginlbl;
    
    @FXML
    private Label userNamelbl;

    @FXML
    private Label passwordlbl;

    @FXML
    private TextField userNametxt;

    @FXML
    private PasswordField passwordPswrd;
    
    @FXML
    private Button submitBtn;
    
    @FXML
    private Button closeBtn;
    
   
    /**
     * 
     * @param event
     * @throws IOException 
     * Logs the user in and display upcoming appointments
     */
    @FXML
    public void submitHandler(ActionEvent event) throws IOException {
        
        
        String userName = userNametxt.getText();
        String password = passwordPswrd.getText();
        
        
        String filename = "LoginAttemps.txt";
        //try with resources closes resources automatically
        try(
        FileWriter fwr = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwr)){
        LocalDateTime ldt = LocalDateTime.now();
        
        
        UserDAO.login(userName, password);
       
        //if the username and password dont have a match, the user isn't logged in and the attempt is recorded
        if(UserDAO.currentUser.getUserName() == null) {
            
           errorlbl.setText(ResourceBundle.getBundle("Resources/rb").getString("errorlbl"));
           outputFile.println("Login attempt failed " + userName + " " + ldt);
           outputFile.close();
        } else {

            try {
                
                ObservableList<Appointment> apps;
                apps = FXCollections.observableArrayList(AppointmentDAO.getAppointments());

                 
                if(apps.stream().filter(app -> app.getStart().isAfter(LocalDateTime.now()) && app.getStart().isBefore(LocalDateTime.now().plusMinutes(15))).count() > 0) {
                    
                    //using a lambda expressions, filter through the appointments to find any within the next 15 minutes and concatenate the customer names with a new line for each. Using al labda expression makes the code much cleaner and easier to follow
                    String upcomingApps = apps.stream()
                                              .filter(app ->  app.getStart().isAfter(LocalDateTime.now()) && app.getStart().isBefore(LocalDateTime.now().plusMinutes(15)))
                                              .map(app -> app.getCustomerName())
                                              .collect(Collectors.joining("\n"));
                    //show an alert to the user with all the appointments within the next 15 minutes
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Upcoming appointment(s) with \n" + upcomingApps);
                    alert.showAndWait();
                }  
            } catch (SQLException ex) {
                System.out.println(ex);
                return;
            }
            //record the successful login attempt then close the txt file
            outputFile.println("Login successful " + userName + " " + ldt);
            outputFile.close();
            //if the user logs in with admin credentials then go to the admin screen
            if (userName.equals("admin")) {
                Parent mainView = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                Scene addPartScene = new Scene(mainView);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(addPartScene);
                window.show();
            }else {
                Parent mainView = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene addPartScene = new Scene(mainView);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(addPartScene);
                window.show();
                }
            }
        }
        
    }
    /**
     * 
     * @param event 
     * Closes the connection when the user closes the application
     */
    @FXML
    public void closeHandler(ActionEvent event){
      
            try {
                DBConnection.closeConnection();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            System.exit(0);
        
    }
   
    /**
     * 
     * @param url
     * @param rb 
     * connects to the database when the application id opened
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Locale.setDefault(new Locale("fr"));
        
        
        try {
            DBConnection.makeConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        //get the language resources depending on locale
        rb = ResourceBundle.getBundle("Resources/rb", Locale.getDefault());
        
       
        userNamelbl.setText(rb.getString("userNamelbl"));
        submitBtn.setText(rb.getString("submitBtn"));
        loginlbl.setText(rb.getString("loginlbl"));
        passwordlbl.setText(rb.getString("passwordlbl"));
        closeBtn.setText(rb.getString("closeBtn"));
    }    
    
}
