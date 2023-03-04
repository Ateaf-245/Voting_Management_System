package com.example.votingsystem.Controllers;

import com.example.votingsystem.Controllers.AdminControllers.AdminLoginController;
import com.example.votingsystem.Controllers.UserControllers.VoterLoginController;
import com.example.votingsystem.Databases.getAlert;
import com.example.votingsystem.Databases.getData;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This contains Method for Admin and user to Login.
 */
public class LoginController implements Initializable {

    @FXML
    public MFXButton Login_LoginBtn;

    @FXML
    public MFXPasswordField Login_Password;

    @FXML
    public Hyperlink Login_Register;

    @FXML
    public ComboBox<?> Login_UserType;

    @FXML
    public MFXTextField Login_Username;



    private String[] userType = {"Admin", "Voter"};
    /**
     * This Method set the User Type as Admin and Voter.
     */
    public void loginUserTypeList(){
        // to disable to register link before selecting the user type
        Login_Register.setVisible(false);
        List<String> list = new ArrayList<>();

        for (String data :userType){
            list.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(list);
        Login_UserType.setItems(listData);

        if(Login_UserType.getSelectionModel().getSelectedItem() == "Admin"){
            // disabling register link if user type is Admin
            Login_Register.setVisible(false);
            Login_Username.setPromptText("Username");
        }
        if(Login_UserType.getSelectionModel().getSelectedItem() == "Voter"){
            // enabling register link if user type is Voter
            Login_Register.setVisible(true);
            Login_Username.setPromptText("Voter ID");
        }
    }

    private double x = 0;
    private double y = 0;

    /**
     * This Method takes user input and check their credentials and open appropriate Dashboard based on user type.
     */
    public void login(){

        String username = Login_Username.getText();
        String password = Login_Password.getText();
        try {

            if(Login_UserType.getSelectionModel().getSelectedItem() == null){
                getAlert.alertUserType();
            }else if (username.isEmpty() || password.isEmpty()) {
                getAlert.alertErrorText();
            }else{

                Parent root ;
                if (Login_UserType.getSelectionModel().getSelectedItem() == "Admin") {

                    if (AdminLoginController.login(username, password)) {

                        getData.username = username.toUpperCase();

                        Login_LoginBtn.getScene().getWindow().hide();
                        root = FXMLLoader.load(getClass().getResource("/FXML/AdminDashboard.fxml"));
                        move(root);
                    } else {
                        Login_Username.setText("");
                        Login_Password.setText("");
                    }
                } else if (Login_UserType.getSelectionModel().getSelectedItem() == "Voter") {

                    if (VoterLoginController.login(username, password)) {

                        getData.username = username;
                        Login_LoginBtn.getScene().getWindow().hide();
                        root = FXMLLoader.load(getClass().getResource("/FXML/VoterDashboard.fxml"));
                        move(root);
                    } else {
                        Login_Username.setText("");
                        Login_Password.setText("");
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * This Method is called by Login Method once user credentials are verified.
     */
    public void move(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {    // to drag the application
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setTitle("Voting Management System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This Method redirect the Voter to registration form to create new account.
     */
    public void loginRegisterLink(){
        try {
            Login_LoginBtn.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/VoterRegister.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent event) -> {    // to drag the application
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginUserTypeList();
    }
}