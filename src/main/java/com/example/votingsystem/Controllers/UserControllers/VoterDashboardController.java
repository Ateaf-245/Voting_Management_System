package com.example.votingsystem.Controllers.UserControllers;

import com.example.votingsystem.Databases.CandidateData;
import com.example.votingsystem.Databases.Database;
import com.example.votingsystem.Databases.getAlert;
import com.example.votingsystem.Databases.getData;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class VoterDashboardController implements Initializable {

    @FXML
    public VBox Candidate_Layout;

    @FXML
    public Label Dashboard_Name;

    @FXML
    public Label Dashboard_VoterID;

    @FXML
    public Label Dashboard_VoterStatus;

    @FXML
    public AnchorPane Dashboard_form;

    @FXML
    public MFXButton Profile_CangePassword_SubmitBtn;

    @FXML
    public Hyperlink Profile_ChanagePassword_CancelBtn;

    @FXML
    public Hyperlink Profile_ChangePassword_RequestBtn;

    @FXML
    public MFXTextField Profile_ConfirmPasswordtxt;

    @FXML
    public Label Profile_Firstname;

    @FXML
    public Label Profile_LastName;

    @FXML
    public Label Profile_Location;

    @FXML
    public MFXTextField Profile_NewPasswordtxt;

    @FXML
    public Label Profile_Phone;

    @FXML
    public Label Profile_VoterID;

    @FXML
    public Label Profile_VotingStatus;

    @FXML
    public AnchorPane Profile_form;

    @FXML
    public MFXTextField Profile_oldPasswordtxt;

    @FXML
    public AnchorPane Results_form;

    @FXML
    public MFXButton LogoutBtn;

    @FXML
    public Circle Main_Image;

    @FXML
    public MFXButton ProfileBtn;

    @FXML
    public MFXButton ResultsBtn;

    @FXML
    public MFXButton DashBoradBtn;

    @FXML
    public MFXButton Voter_Result_ElectionResultBtn;

    @FXML
    public Label Voter_Result_ElectionStatus;

    @FXML
    public PieChart Voter_Result_PieChart;

    @FXML
    public Label Voter_Result_WinnerName;

    @FXML
    public Label Voter_Result_WinnerParty;

    @FXML
    public Label Voter_Result_WinnerPercentage;

    @FXML
    public Circle Voter_Result_WinnerPhoto;

    @FXML
    public Label Voter_Result_WinnerVotes;

    @FXML
    public AnchorPane Voter_Result_Winner_form;

    @FXML
    public Label Voter_Name;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    String  Voter_id =  getData.username;

    /**
     * This Method is used to set Voter Image on Voter Dashboard screen.
     */
    public void ImportVoterImage(){

        StringBuilder url = new StringBuilder("file:");
        String sql ="SELECT photo from voter WHERE ID ='"+Voter_id+"'";
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
          result = prepare.executeQuery();

          while (result.next()){
              url.append(result.getString("photo"));
          }
            Image img = new Image(url.toString());
            Main_Image.setFill(new ImagePattern(img));
        }catch(Exception e){e.printStackTrace();}
    }

//    DashBoard

    /**
     * This Method Sets the user details in the Profile section of the Dashboard.
     */
    public void Dashboard_setProfile(){
        String sql = "SELECT firstName, lastName, status FROM voter WHERE ID ="+Voter_id+"";
        connect = Database.connectDb();

        String name ="", status="";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                name = result.getString("firstName");
                name +=" " + result.getString("lastName");
                status = result.getString("status");
            }
            Dashboard_Name.setText(name);
            Dashboard_VoterID.setText(Voter_id);
            if (Objects.equals(status, "0")){
                Dashboard_VoterStatus.setText("Not Voted");
                Dashboard_VoterStatus.setStyle("-fx-text-fill : #ff0000");
            }
            else {
                Dashboard_VoterStatus.setText("Voted");
                Dashboard_VoterStatus.setStyle("-fx-text-fill : #1dae00");
            }
        }catch (Exception e){e.printStackTrace();}
    }

    int temp;
    private ObservableList<CandidateData> dashboardCandidateList;
    /**
     * This Method calls CandidateItem method to show the Candidates on the screen and if user click on vote button then it will update the votes counts of particular Candidate and call the dashboardCandidateShowListData1() Method to disable the vote button.
     */
    public void dashboardCandidateShowListData(){

        String sql ="SELECT status FROM voter WHERE ID ="+Voter_id+"";
        connect = Database.connectDb();
        int voter_status = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                voter_status = result.getInt("status");
            }
            if (voter_status == 1){
                dashboardCandidateShowListData1();
            }else {

                dashboardCandidateList =CandidateDetails.dashboardCandidateListData();
                for (int i=0; i<dashboardCandidateList.size();i++){

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/FXML/CandidateItem.fxml"));

                    try{
                        HBox hBox = fxmlLoader.load();
                        CandidateItem candidateItem = fxmlLoader.getController();
                        candidateItem.setData(dashboardCandidateList.get(i));

                        int finalI1 = i;
                        candidateItem.CandidateItem_VoteBtn.setOnMouseClicked(mouseEvent -> {
                            DashboardUpdateVoterStatus();
                            Candidate_Layout.getChildren().clear();
                            temp = finalI1;
                            dashboardCandidateShowListData1();
                        });

                        Candidate_Layout.getChildren().add(hBox);
                    }catch (Exception e){e.printStackTrace();}
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * This Method to disables the vote button once the user has voted.
     */
    public void dashboardCandidateShowListData1(){

        dashboardCandidateList = CandidateDetails.dashboardCandidateListData();
        for (int i=0; i<dashboardCandidateList.size();i++){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXML/CandidateItem.fxml"));

            try{
                HBox hBox = fxmlLoader.load();
                CandidateItem candidateItem = fxmlLoader.getController();
                candidateItem.setData(dashboardCandidateList.get(i));

                if (temp == i) {
                    candidateItem.CandidateItem_VoteBtn.setText("Voted");
                    candidateItem.CandidateItem_VoteBtn.setDisable(true);
                }else{
                    candidateItem.CandidateItem_VoteBtn.setText("Vote");
                    candidateItem.CandidateItem_VoteBtn.setDisable(true);
                }
                Candidate_Layout.getChildren().add(hBox);
            }catch (Exception e){e.printStackTrace();}
        }
    }

    /**
     * This Method update Voter's Voting status as voted.
     */

    public void DashboardUpdateVoterStatus(){
        String sql ="UPDATE voter SET status = 1 WHERE ID ="+Voter_id+"";
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

//    Voting Result

    /**
     * This Method hides the Results details so that user can't see before the end of the election.
     */
    public void votingResultDefault(){

        Voter_Result_PieChart.setVisible(false);
        Voter_Result_Winner_form.setVisible(false);
        Voter_Result_WinnerPhoto.setVisible(false);

    }

    /**
     * This Method Shows the voting result after the election is completed.
     */
    public void votingResultViewResult(){
    String sql = "SELECT election_status FROM candidate";
    connect = Database.connectDb();
    int status =0;
    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

       while (result.next()) {
            status = result.getInt("election_status");
        }
        if (status == 0) {
            getAlert.alertVoterElectionStatus();
        } else  if (status == 1) {
            getAlert.alertVoterElectionStatusForResult();
        }else {

            Voter_Result_PieChart.setVisible(true);
            Voter_Result_Winner_form.setVisible(true);
            Voter_Result_WinnerPhoto.setVisible(true);

            votingResultPieChartData();
            votingResultWinner();
        }
    }catch (Exception e){e.printStackTrace();}
}

    private ObservableList<CandidateData> votingResultCandidateList;
    public void votingResultPieChartData(){

        votingResultCandidateList = CandidateDetails.dashboardCandidateListData();
        ObservableList<PieChart.Data> PieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < votingResultCandidateList.size(); i++) {

            String name = setCandidateName(votingResultCandidateList.get(i));
            int Votes =  setCandidateVotes(votingResultCandidateList.get(i));

            PieChartData.add( new PieChart.Data(name,Votes));
        }
        PieChartData.forEach(data -> {
            data.nameProperty().bind(Bindings.concat(data.getName()," ",data.pieValueProperty()));
        });
        Voter_Result_PieChart.getData().addAll(PieChartData);
    }

    public String setCandidateName(CandidateData candidateData){
        return candidateData.getFirstName()+" "+candidateData.getLastName();
    }

    public int setCandidateVotes(CandidateData candidateData){
        return candidateData.getVotes();
    }

    public void votingResultWinner(){
        String winner = "SELECT firstName, lastName, party, photo , votes FROM `candidate`HAVING MAX(votes);";
        String percentage = "SELECT SUM(votes) FROM `candidate`";
        connect = Database.connectDb();

        String winnerName = "", party = "";
        StringBuilder url = new StringBuilder("file:");
        int totalVotes=0, votes =0, winningPercentage =0;

        try{
            prepare = connect.prepareStatement(winner);
            result = prepare.executeQuery();

            while (result.next()){
                winnerName = result.getString("firstName") +" "+ result.getString("lastName");
                party = result.getString("party");
                votes = result.getInt("votes");
                url.append(result.getString("photo"));
            }

            Image img = new Image(url.toString());
            Voter_Result_WinnerPhoto.setFill(new ImagePattern(img));

            Voter_Result_WinnerName.setText(winnerName);
            Voter_Result_WinnerParty.setText(party);
            Voter_Result_WinnerVotes.setText(String.valueOf(votes));

            prepare = connect.prepareStatement(percentage);
            result = prepare.executeQuery();

            while (result.next()) {
                totalVotes = result.getInt("SUM(votes)");
            }

            winningPercentage = (votes * 100)/totalVotes;
            Voter_Result_WinnerPercentage.setText(String.valueOf(winningPercentage)+"%");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //   Profile
    public void profileShowData(){
        String sql = "SELECT ID , firstName, lastName, mobile, location, status from voter WHERE ID ="+Voter_id;
        connect = Database.connectDb();

        String id = "", firstName = "", lastName = "", mobile = "", location = "", status = "";
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                id = (result.getString("ID"));
                firstName = (result.getString("firstName"));
                lastName = (result.getString("lastName"));
                mobile = (result.getString("mobile"));
                location = (result.getString("location"));
                status = (result.getString("status"));
            }

            Profile_VoterID.setText(id);
            Profile_Firstname.setText(firstName);
            Profile_LastName.setText(lastName);
            Profile_Phone.setText(mobile);
            Profile_Location.setText(location);

            if (Objects.equals(status, "0")){
                Profile_VotingStatus.setText("Not Voted");
                Profile_VotingStatus.setStyle("-fx-text-fill : #ff0000");
            }
            else {
                Profile_VotingStatus.setText("Voted");
                Profile_VotingStatus.setStyle("-fx-text-fill : #1dae00");
            }
        }catch(Exception e){e.printStackTrace();}
    }

    public void profilePasswordChangeRequest(){

        Profile_oldPasswordtxt.setVisible(true);
        Profile_NewPasswordtxt.setVisible(true);
        Profile_ConfirmPasswordtxt.setVisible(true);
        Profile_CangePassword_SubmitBtn.setVisible(true);
        Profile_ChanagePassword_CancelBtn.setVisible(true);
    }

    public void profilePasswordChangeSubmit(){
        String oldPassword = "SELECT password FROM voter WHERE ID ="+Voter_id+"";
        String sql = "UPDATE voter SET password ="+Profile_NewPasswordtxt.getText()+" WHERE ID ="+Voter_id+"";
        connect= Database.connectDb();
        try{
            prepare = connect.prepareStatement(oldPassword);
            result = prepare.executeQuery();
            String oldPassword1 ="";
            while(result.next()){
                oldPassword1 = result.getString("password");
            }

            if (Profile_oldPasswordtxt.getText().isEmpty()|| Profile_NewPasswordtxt.getText().isEmpty() || Profile_ConfirmPasswordtxt.getText().isEmpty()){
                getAlert.alertErrorText();

            } else if (!Objects.equals(oldPassword1, Profile_oldPasswordtxt.getText())){
                getAlert.alertIncorrectPassword();

            } else if (!Objects.equals(Profile_NewPasswordtxt.getText(), Profile_ConfirmPasswordtxt.getText())) {
                getAlert.alertErrorPassword();
            }else {
                if (getAlert.alertPasswordChangeConfirmation()) {
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    getAlert.alertPasswordChanged();
                    profilePasswordChangeCancel();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void profilePasswordChangeCancel(){

        Profile_oldPasswordtxt.setText("");
        Profile_NewPasswordtxt.setText("");
        Profile_ConfirmPasswordtxt.setText("");

        Profile_oldPasswordtxt.setVisible(false);
        Profile_NewPasswordtxt.setVisible(false);
        Profile_ConfirmPasswordtxt.setVisible(false);
        Profile_CangePassword_SubmitBtn.setVisible(false);
        Profile_ChanagePassword_CancelBtn.setVisible(false);
    }

    public void displayUsername(){
        String sql = "SELECT firstName from voter WHERE ID ="+Voter_id;
        connect = Database.connectDb();

        String firstName = "";
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                firstName = (result.getString("firstName"));
            }
            Voter_Name.setText(firstName.toUpperCase(Locale.ROOT));
        }catch(Exception e){e.printStackTrace();}
    }

    public void defaultNavigation() {
        Dashboard_form.setVisible(false);
        Results_form.setVisible(false);
        Profile_form.setVisible(true);

        DashBoradBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
        ResultsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
        ProfileBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");

        profilePasswordChangeCancel();
    }

    public void checkElectionStatus(){

        String sql = "SELECT election_status FROM candidate";
        connect = Database.connectDb();
        int status =0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                status = result.getInt("election_status");
            }
            if (status==0){

                Dashboard_form.setVisible(false);
                Results_form.setVisible(false);
                Profile_form.setVisible(true);

                getAlert.alertVoterElectionStatus();

                DashBoradBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
                ResultsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
                ProfileBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");

                profilePasswordChangeCancel();
            }

        }catch (Exception e){e.printStackTrace();}
    }

    public void SwitchForm(ActionEvent event){
        if (event.getSource() == DashBoradBtn){

            Dashboard_form.setVisible(true);
            Results_form.setVisible(false);
            Profile_form.setVisible(false);

            DashBoradBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
            ResultsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            ProfileBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

            checkElectionStatus();

        } else if  (event.getSource() == ResultsBtn){
            Dashboard_form.setVisible(false);
            Results_form.setVisible(true);
            Profile_form.setVisible(false);

            DashBoradBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            ResultsBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
            ProfileBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

            votingResultDefault();

        } else  if (event.getSource() == ProfileBtn){
            Dashboard_form.setVisible(false);
            Results_form.setVisible(false);
            Profile_form.setVisible(true);

            DashBoradBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            ResultsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            ProfileBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");

            profilePasswordChangeCancel();
        }
    }

    private double x = 0;
    private double y = 0;

    public void logout(){
        try {
            if (getAlert.alertLogout()){
                LogoutBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) ->{
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
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUsername();
        defaultNavigation();
        ImportVoterImage();

        Dashboard_setProfile();
        dashboardCandidateShowListData();

        profileShowData();
    }
}