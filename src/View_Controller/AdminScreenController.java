/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAO.DBConnection;
import DAO.DentistDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class AdminScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField addUserTxt;

    @FXML
    private TextField userPasswordTxt;

    @FXML
    private TextField dentistNameTxt;

    @FXML
    private Button saveUserbtn;

    @FXML
    private Button saveDentistBtn;

    @FXML
    private Button signOutBtn;
    
    @FXML
    public void saveUserHandler(ActionEvent event) {
        if (addUserTxt.getText() == null || userPasswordTxt.getText() == null) {
            return;
        }else {
            UserDAO.addUser(addUserTxt.getText(), userPasswordTxt.getText());
        }
        
    }
    
    @FXML
    public void saveDentistHandler(ActionEvent event) {
        if (dentistNameTxt.getText() == null) {
            return;
        } else {
            DentistDAO.insertDentist(dentistNameTxt.getText());
        }
    }
    
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
            Parent mainView = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene addPartScene = new Scene(mainView);
        
        //Get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
