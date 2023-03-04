package com.example.votingsystem.Databases;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class getAlert {
    static Alert alert;

    public static void alertErrorDatabaseConnection(){
        alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Unable to connect to Database");
        alert.showAndWait();
    }

    public static void alertErrorText(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }

    public static void alertUserType(){
        alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please select the user type !");
        alert.showAndWait();
    }

    public static void alertErrorImage(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Please Upload the Photo!");
        alert.showAndWait();
    }

    public static void alertErrorPassword(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Password doesn't match!");
        alert.showAndWait();
    }

    public static void alertIncorrectPassword(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Incorrect Password");
        alert.showAndWait();
    }

    public static boolean alertPasswordChangeConfirmation(){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to Change the password ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static void alertPasswordChanged(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Password successfully changed");
        alert.showAndWait();
    }

    public static void alertRegisterInformation( String Voter_ID){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Information Message");
        alert.setContentText("Successfully Submitted your request!\n Please wait till it get approved. \n Your Voter ID : "+Voter_ID);
        alert.showAndWait();
    }

    public static void alertSuccessfulLogin(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully login");
        alert.showAndWait();
    }

    public static boolean alertLogout(){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static void alertIncorrectCredentials(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Wrong Username or Password");
        alert.showAndWait();
    }

    public static void alertVerificationPending(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Your request is not yet approved!");
        alert.showAndWait();
    }

    public static void alertVoterElectionStatus(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Election is not yet stated");
        alert.showAndWait();
    }

    public static void alertVoterElectionStatusForResult(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Please wait for Election to complete");
        alert.showAndWait();
    }

    public static void alertVoterElectionStatusPart2(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("You need to reset old Election in order to create new one!");
        alert.showAndWait();
    }

    public static void alertVoterElectionStatusPart3(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("The Election is already marked as completed!");
        alert.showAndWait();
    }

    public static void alertElectionStatus(){
        alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please select the Election Status first!");
        alert.showAndWait();
    }

    public static void alertElectionResetError(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Can't reset the Election as the Election is still going on!");
        alert.showAndWait();
    }
    public static void alertElectionAddCandidateError(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Can't Add new Candidate as the Election is still going on!");
        alert.showAndWait();
    }

 public static void alertElectionDeleteCandidateError(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Can't Delete Candidate as the Election is still going on!");
        alert.showAndWait();
    }

    public static boolean alertElectionReset(){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to reset ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static boolean alertElectionStart(){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to Start the Election?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static boolean alertElectionComplete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to Complete the Election ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static void alertSuccessfullyAdded() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Added");
        alert.showAndWait();
    }

    public static boolean alertCandidateUpdate(String ID){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to UPDATE Candidate ID: "+ID);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static void alertSuccessfullyUpdated() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Updated");
        alert.showAndWait();
    }

    public static void alertCandidateSelect() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error Message");
        alert.setContentText("Please select the entry entry first! ");
        alert.showAndWait();
    }

     public static boolean alertCandidateDelete(String ID){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to Delete Candidate ID: "+ID);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)){
            return true;
        }else
            return false;
    }

    public static void alertSuccessfullyDeleted() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Deleted");
        alert.showAndWait();
    }
}
