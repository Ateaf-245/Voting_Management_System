package com.example.votingsystem.Controllers.AdminControllers;

import com.example.votingsystem.Controllers.UserControllers.CandidateDetails;
import com.example.votingsystem.Databases.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    public AnchorPane Admin_CandidatesDetails_form;

    @FXML
    public AnchorPane Admin_Home_form;

    @FXML
    public AnchorPane Admin_VoterDetails_form;

    @FXML
    public AnchorPane Admin_VoterVerification_form;

    @FXML
    public AnchorPane Admin_VotingResult_form;

    @FXML
    public MFXButton LogoutBtn;

    @FXML
    public VBox VoterVerification_Layout;

    @FXML
    public MFXTableView<VoterData> VoterDetails_table;

    @FXML
    public Label VoterDetails_PendingApproval;

    @FXML
    public Label VoterDetails_TotalNumber;

    @FXML
    public Label Home_ElectionStatus;

    @FXML
    public MFXButton Home_Election_Reset;

    @FXML
    public ComboBox<?> Home_Election_Status;

    @FXML
    public MFXButton Home_Election_Submit;

    @FXML
    public PieChart Home_PieChart;

    @FXML
    public Label Home_TotalCandidate;

    @FXML
    public Label Home_TotalVoter;

    @FXML
    public MFXButton CandidatesDetailsBtn;

    @FXML
    public MFXButton HomeBtn;

    @FXML
    public MFXButton VoterDetailsBtn;

    @FXML
    public MFXButton VoterVerifybtn;

    @FXML
    public MFXButton VotingResultBtn;

    @FXML
    public Label UserName;

    @FXML
    public MFXButton Candidate_AddBtn;

    @FXML
    public MFXButton Candidate_ClearBtn;

    @FXML
    public MFXButton Candidate_DeleteBtn;

    @FXML
    public MFXTextField Candidate_FirstName;

    @FXML
    public Label Candidate_ID;

    @FXML
    public ImageView Candidate_Image;

    @FXML
    public MFXButton Candidate_Image_UploadBtn;

    @FXML
    public MFXTextField Candidate_LastName;

    @FXML
    public MFXTextField Candidate_Location;

    @FXML
    public MFXTextField Candidate_PartyName;

    @FXML
    public MFXTextField Candidate_Phone;

    @FXML
    public MFXTextField Candidate_Search;

    @FXML
    public TableView<CandidateData> Candidate_TableView;

    @FXML
    public TableColumn<CandidateData, String> Candidate_Table_FirstName;

    @FXML
    public TableColumn<CandidateData, String> Candidate_Table_ID;

    @FXML
    public TableColumn<CandidateData, String> Candidate_Table_LastName;

    @FXML
    public TableColumn<CandidateData, String> Candidate_Table_Location;

    @FXML
    public TableColumn<CandidateData, String> Candidate_Table_PartyName;

    @FXML
    public TableColumn<CandidateData, String> Candidate_Table_Phone;

    @FXML
    public MFXButton Candidate_UpdateBtn;

    @FXML
    public Label Result_ElectionStatus;

    @FXML
    public PieChart Result_PieChart;

    @FXML
    public Label Result_WinnerName;

    @FXML
    public Label Result_WinnerParty;

    @FXML
    public Circle Result_WinnerPhoto;

    @FXML
    public Label Result_WinnerVotes;

    @FXML
    public Label Result_WinnerPercentage;

    @FXML
    public AnchorPane Result_Winner_form;


    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

//    Home
    public  void HomeTotalCandidates(){

    String sql = "SELECT COUNT(ID) FROM candidate";

    connect = Database.connectDb();
    int countData = 0 ;
    try{
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        while(result.next()){
            countData = result.getInt("COUNT(ID)");
        }
        Home_TotalCandidate.setText(String.valueOf(countData));

    }catch (Exception e){e.printStackTrace();}
}

    private String[] ElectionStatusList ={"Start","Completed"};
    public void HomeElectionStatuslist(){
        List<String> listE = new ArrayList<>();

        for (String data: ElectionStatusList) {
            listE.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listE);
        Home_Election_Status.setItems(list);

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
                Home_ElectionStatus.setText("New");
            } else if (status == 1) {
                Home_ElectionStatus.setText("In Progress");
            } else  if (status == 2)  {
                Home_ElectionStatus.setText("Completed");
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void HomeElection_Update(){

        String sql = "SELECT election_status FROM candidate";
        connect = Database.connectDb();
        int status =0;
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                status = result.getInt("election_status");
            }

            if(Home_Election_Status.getSelectionModel().getSelectedItem() == null) {
                getAlert.alertElectionStatus();

            } else if(Objects.equals(Home_Election_Status.getSelectionModel().getSelectedItem().toString(), "Start")) {

                if (status == 0) {
                    if (getAlert.alertElectionStart()) {
                        UpdateElectionStatus.candidateUpdateElectionStatus(1);
                        Home_ElectionStatus.setText("in Progress");
                        Result_ElectionStatus.setText("in Progress");
                    }
                }  else if (status == 1) {
                    getAlert.alertVoterElectionStatusForResult();
                } else  if (status == 2)  {
                    getAlert.alertVoterElectionStatusPart2();
                }

            }else if(Objects.equals(Home_Election_Status.getSelectionModel().getSelectedItem().toString(), "Completed")) {

                if (status == 0) {
                    getAlert.alertElectionStatus();
                }  else if (status == 1) {
                    if (getAlert.alertElectionComplete()) {
                        UpdateElectionStatus.candidateUpdateElectionStatus(2);
                        Home_ElectionStatus.setText("Completed");
                        Result_ElectionStatus.setText("Completed");
                    }
                } else  if (status == 2)  {
                    getAlert.alertVoterElectionStatusPart3();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void HomeElection_Reset() {

        String sql1 = "SELECT * FROM Candidate WHERE election_status = 1";

        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql1);
            result = prepare.executeQuery();

            if (result.next()) {

                getAlert.alertElectionResetError();

            } else if (getAlert.alertElectionReset()) {

                Home_Election_Status.getSelectionModel().clearSelection();
                UpdateElectionStatus.candidateResetElectionStatus();
                Home_ElectionStatus.setText("New");
                Result_ElectionStatus.setText("New");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HomePieChartData() {

        int verifiedVoters = 0, unVerifiedVoters = 0 ;

        connect = Database.connectDb();
        try {
            // getting number ov verified user
            String sql1 = "SELECT COUNT(ID) FROM voter WHERE verification = 1";
            prepare = connect.prepareStatement(sql1);
            result = prepare.executeQuery();

            while (result.next()) {
                verifiedVoters = result.getInt("COUNT(ID)");
            }

            // getting number of unverified user
            String sql2 = "SELECT COUNT(ID) FROM voter WHERE verification = 0";
            prepare = connect.prepareStatement(sql2);
            result = prepare.executeQuery();

            while (result.next()) {
                unVerifiedVoters = result.getInt("COUNT(ID)");
            }

            ObservableList<PieChart.Data> PieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Verified Voter", verifiedVoters),
                    new PieChart.Data("Pending", unVerifiedVoters));

            PieChartData.forEach(data -> {
                data.nameProperty().bind(Bindings.concat(data.getName()," ",data.pieValueProperty()));
            });

            Home_PieChart.getData().addAll(PieChartData);

        }catch (Exception e){e.printStackTrace();}
    }

    private Image image;
    File file;
    String rootDirectory;
    String destDirectory;

//    Candidates Details
    public void candidateUploadImage(){
        FileChooser open = new FileChooser();
        file = open.showOpenDialog(Admin_CandidatesDetails_form.getScene().getWindow());

        if (file!=null){

            rootDirectory = file.getParent()+"\\"+file.getName();
            destDirectory = "../VotingSystem/src/main/resources/Images/Candidates/";

            getData.path = rootDirectory;

            image = new Image(file.toURI().toString(),115,135,false,true);
            Candidate_Image.setImage(image);
        }
    }

    public boolean checkElectionStatus() {
        String sql1 = "SELECT * FROM Candidate WHERE election_status = 1";

        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql1);
            result = prepare.executeQuery();

            if (result.next())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void candidateAdd(){

        String sql = "INSERT INTO candidate " +
                "(firstName, LastName, phone, location, party, photo, votes, election_status)" +
                "VALUES(?,?,?,?,?,?,0,0)";

        connect = Database.connectDb();

        try{
            if (Candidate_FirstName.getText().isEmpty()
            || Candidate_LastName.getText().isEmpty()
            || Candidate_Phone.getText().isEmpty()
            || Candidate_Location.getText().isEmpty()
            || Candidate_PartyName.getText().isEmpty()){

                getAlert.alertErrorText();

            }else if (Objects.equals(getData.path, "") || getData.path == null){
                getAlert.alertErrorImage();
            }else {
                if (checkElectionStatus()){
                    getAlert.alertElectionAddCandidateError();
                }else {
                    getImageMoved.ImageMover(rootDirectory,destDirectory,file);

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1,Candidate_FirstName.getText());
                    prepare.setString(2,Candidate_LastName.getText());
                    prepare.setString(3,Candidate_Phone.getText());
                    prepare.setString(4,Candidate_Location.getText());
                    prepare.setString(5,Candidate_PartyName.getText());
                    prepare.setString(6,getData.path);
                    prepare.executeUpdate();

                    getAlert.alertSuccessfullyAdded();

                    candidateShowlist();
                    candidateClear();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void candidateUpdate(){

        String sql = "UPDATE candidate SET " +
                "firstName = '"+Candidate_FirstName.getText()+"', LastName = '"+Candidate_LastName.getText()+"', phone = '"+Candidate_Phone.getText()+"'," +
                " location  = '"+Candidate_Location.getText()+"', party  = '"+Candidate_PartyName.getText()+"', photo  = '"+getData.path+"'";

        connect = Database.connectDb();

        try{
            if (getData.path ==null || getData.path.equals(""))
            getAlert.alertErrorImage();
            else if (Candidate_FirstName.getText().isEmpty()
                    || Candidate_LastName.getText().isEmpty()
                    || Candidate_Phone.getText().isEmpty()
                    || Candidate_Location.getText().isEmpty()
                    || Candidate_PartyName.getText().isEmpty())
                getAlert.alertErrorText();
            else {
                if (getAlert.alertCandidateUpdate(Candidate_ID.getText())){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    getData.path = "";

                    getAlert.alertSuccessfullyUpdated();
                    candidateShowlist();
                    candidateClear();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void candidateDelete(){

        String sql = "DELETE FROM candidate WHERE ID = '"+Candidate_ID.getText()+"'";
        connect = Database.connectDb();

        try {
            if (checkElectionStatus()) {
                getAlert.alertElectionDeleteCandidateError();
            }else if (Candidate_ID.getText().isEmpty()) {
                getAlert.alertCandidateSelect();
            } else {
                if (getAlert.alertCandidateDelete(Candidate_ID.getText())) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    getAlert.alertSuccessfullyDeleted();
                    candidateShowlist();
                    candidateClear();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void candidateClear(){
        Candidate_ID.setText("");
        Candidate_FirstName.setText("");
        Candidate_LastName.setText("");
        Candidate_Phone.setText("");
        Candidate_Location.setText("");
        Candidate_PartyName.setText("");
        Candidate_Image.setImage(null);
    }

    public void CandidateSelect(){
        CandidateData candidateData = Candidate_TableView.getSelectionModel().getSelectedItem();
        int num = Candidate_TableView.getSelectionModel().getSelectedIndex();

        if ((num -1)< -1){return;}

        Candidate_ID.setText(String.valueOf(candidateData.getCandidate_Id()));
        Candidate_FirstName.setText(String.valueOf(candidateData.getFirstName()));
        Candidate_LastName.setText(String.valueOf(candidateData.getLastName()));
        Candidate_Phone.setText(String.valueOf(candidateData.getPhone()));
        Candidate_Location.setText(String.valueOf(candidateData.getLocation()));
        Candidate_PartyName.setText(String.valueOf(candidateData.getParty()));

        getData.path = candidateData.getImage();
        String url = "file:"+ candidateData.getImage();

        image = new Image(url,115,135,false,true);
        Candidate_Image.setImage(image);
    }

    public void CandidateSearch(){

        FilteredList<CandidateData> filter = new FilteredList<>(candidateList, e-> true);

        Candidate_Search.textProperty().addListener((observable, oldValue, newValue) ->{

            filter.setPredicate(predicateEmployeeData -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String searchKey = newValue.toLowerCase();

                if(predicateEmployeeData.getCandidate_Id().toString().contains(searchKey)){
                    return true;
                }else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateEmployeeData.getPhone().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateEmployeeData.getLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateEmployeeData.getParty().toLowerCase().contains(searchKey)){
                    return true;
                }else return false;
            });
        });
        SortedList<CandidateData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(Candidate_TableView.comparatorProperty());
        Candidate_TableView.setItems(sortList);
    }

    public ObservableList<CandidateData> candidateListData(){

        ObservableList<CandidateData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM candidate";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            CandidateData candidateData;

            while(result.next()){
                candidateData =new CandidateData(result.getInt("ID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("phone"),
                        result.getString("location"),
                        result.getString("party"),
                        result.getString("photo"));

                listData.add(candidateData);
            }
        }catch (Exception e){e.printStackTrace();}
        return listData;
    }

    private  ObservableList<CandidateData> candidateList;
    public void candidateShowlist(){

    candidateList = candidateListData();

    Candidate_Table_ID.setCellValueFactory(new PropertyValueFactory<>("candidate_Id"));
    Candidate_Table_FirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    Candidate_Table_LastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    Candidate_Table_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    Candidate_Table_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
    Candidate_Table_PartyName.setCellValueFactory(new PropertyValueFactory<>("party"));

    Candidate_TableView.setItems(candidateList);
    CandidateSelect();
    CandidateSearch();
    }

//   Voting Results

    public void votingResultDefault(){

        Result_PieChart.setVisible(false);
        Result_Winner_form.setVisible(false);
        Result_WinnerPhoto.setVisible(false);

    }

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

                Result_PieChart.setVisible(true);
                Result_Winner_form.setVisible(true);
                Result_WinnerPhoto.setVisible(true);

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
        Result_PieChart.getData().addAll(PieChartData);
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
            Result_WinnerPhoto.setFill(new ImagePattern(img));

            Result_WinnerName.setText(winnerName);
            Result_WinnerParty.setText(party);
            Result_WinnerVotes.setText(String.valueOf(votes));

            prepare = connect.prepareStatement(percentage);
            result = prepare.executeQuery();

            while (result.next()) {
                totalVotes = result.getInt("SUM(votes)");
            }

            winningPercentage = (votes * 100)/totalVotes;
            Result_WinnerPercentage.setText(String.valueOf(winningPercentage)+"%");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    Voter's Details
    public  void VoterDetailsTotalVoter(){

        String sql = "SELECT COUNT(ID) FROM voter";

        connect = Database.connectDb();
        int countData = 0 ;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countData = result.getInt("COUNT(ID)");
            }
            VoterDetails_TotalNumber.setText(String.valueOf(countData));
            Home_TotalVoter.setText(String.valueOf(countData));

        }catch (Exception e){e.printStackTrace();}
    }

    public  void VoterDetailsPendingApproval(){

        String sql = "SELECT COUNT(ID) FROM voter WHERE verification = 0";

        connect = Database.connectDb();
        int countData = 0 ;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countData = result.getInt("COUNT(ID)");
            }
            VoterDetails_PendingApproval.setText(String.valueOf(countData));

        }catch (Exception e){e.printStackTrace();}

    }

    public ObservableList<VoterData>  VoterDetailsList(){

        ObservableList<VoterData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM voter WHERE verification = 1";
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            VoterData voterData;

            while (result.next()){
                voterData = new VoterData(result.getInt("ID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("mobile"),
                        result.getString("location"));
                listData.add(voterData);
            }
        }catch (Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<VoterData> VoterList;
    public void VoterDetailsShowList() {

//      creating new column using MaterialFX
        MFXTableColumn<VoterData> VoterID = new MFXTableColumn<>("Voter ID");
        MFXTableColumn<VoterData> FirstName = new MFXTableColumn<>("First Name");
        MFXTableColumn<VoterData> LastName = new MFXTableColumn<>("Last Name");
        MFXTableColumn<VoterData> Mobile = new MFXTableColumn<>("Mobile");
        MFXTableColumn<VoterData> Location = new MFXTableColumn<>("Location");

//      resigning the column width
        VoterID.setPrefWidth(112);
        FirstName.setPrefWidth(113);
        LastName.setPrefWidth(112);
        Mobile.setPrefWidth(112);
        Location.setPrefWidth(112);

//       setting the column
        VoterDetails_table.getTableColumns().addAll(VoterID, FirstName, LastName, Mobile, Location);

//       pulling the list from the database
        VoterList = VoterDetailsList();

//       assigning the value to each column
        VoterID.setRowCellFactory(VoterList -> new MFXTableRowCell<>(VoterData::getVoterID) {{
                setAlignment(Pos.CENTER);
            }});
        FirstName.setRowCellFactory(VoterList -> new MFXTableRowCell<>(VoterData::getFirstName));
        LastName.setRowCellFactory(VoterList -> new MFXTableRowCell<>(VoterData::getLastName));
        Mobile.setRowCellFactory(VoterList -> new MFXTableRowCell<>(VoterData::getPhone));
        Location.setRowCellFactory(VoterList -> new MFXTableRowCell<>(VoterData::getLocation));

//      setting the value on table
        VoterDetails_table.setItems(VoterList);
        VoterDetails_table.getFilters().addAll(
                new IntegerFilter<>("voter Id", VoterData::getVoterID),
                new StringFilter<>("First Name", VoterData::getFirstName),
                new StringFilter<>("Last Name", VoterData::getLastName),
                new StringFilter<>("Mobile", VoterData::getPhone),
                new StringFilter<>("Location", VoterData::getLocation)
        );
    }

//    Voter Verification
    public ObservableList<VoterData>  VoterVerificationListData(){

        ObservableList<VoterData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM voter WHERE verification = 0";
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            VoterData voterData;

            while (result.next()){
                voterData = new VoterData(result.getInt("ID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("mobile"),
                        result.getString("location"),
                        result.getString("photo"),
                        result.getInt("verification"));

                listData.add(voterData);
            }
        }catch (Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<VoterData> VoterVerificationList;
    public void VoterVerificationShowListData(){

        VoterVerificationList = VoterVerificationListData();
        for (int i= 0; i < VoterVerificationList.size(); i++){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXML/VoterVerifyItem.fxml"));

            try{
                HBox hBox = fxmlLoader.load();
                VoterVerifyItem voterItem = fxmlLoader.getController();
                voterItem.setData(VoterVerificationList.get(i));

//              Once the verify button is pressed the list will be cleared and reloaded by calling itself.
                voterItem.VoterItem_VerifyBtn.setOnMouseClicked((mouseEvent) ->{
                    VoterVerification_Layout.getChildren().clear();
                    VoterVerificationShowListData();
                });

                VoterVerification_Layout.getChildren().add(hBox);
            }catch (Exception e){e.printStackTrace();}
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

    public void displayUsername(){
        UserName.setText(getData.username);
    }

    public void defaultNavigation(){
        Admin_Home_form.setVisible(true);
        Admin_CandidatesDetails_form.setVisible(false);
        Admin_VotingResult_form.setVisible(false);
        Admin_VoterDetails_form.setVisible(false);
        Admin_VoterVerification_form.setVisible(false);

        HomeBtn.setStyle("-fx-background-color:  linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
        CandidatesDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
        VotingResultBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
        VoterDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
        VoterVerifybtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

        HomeTotalCandidates();
        HomeElectionStatuslist();
        VoterDetailsTotalVoter();
    }

    public void switchWindow(ActionEvent event){
        if(event.getSource() == HomeBtn) {
            Admin_Home_form.setVisible(true);
            Admin_CandidatesDetails_form.setVisible(false);
            Admin_VotingResult_form.setVisible(false);
            Admin_VoterDetails_form.setVisible(false);
            Admin_VoterVerification_form.setVisible(false);

            HomeBtn.setStyle("-fx-background-color:  linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
            CandidatesDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VotingResultBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterVerifybtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

            HomeTotalCandidates();
            HomeElectionStatuslist();
            VoterDetailsTotalVoter();

        }else  if(event.getSource() == CandidatesDetailsBtn) {
            Admin_Home_form.setVisible(false);
            Admin_CandidatesDetails_form.setVisible(true);
            Admin_VotingResult_form.setVisible(false);
            Admin_VoterDetails_form.setVisible(false);
            Admin_VoterVerification_form.setVisible(false);

            HomeBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)" );
            CandidatesDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
            VotingResultBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterVerifybtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

            candidateShowlist();
            candidateClear();

        }else  if(event.getSource() == VotingResultBtn) {
            Admin_Home_form.setVisible(false);
            Admin_CandidatesDetails_form.setVisible(false);
            Admin_VotingResult_form.setVisible(true);
            Admin_VoterDetails_form.setVisible(false);
            Admin_VoterVerification_form.setVisible(false);

            HomeBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)" );
            CandidatesDetailsBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)" );
            VotingResultBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
            VoterDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterVerifybtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

            votingResultDefault();

        }else  if(event.getSource() == VoterDetailsBtn) {
            Admin_Home_form.setVisible(false);
            Admin_CandidatesDetails_form.setVisible(false);
            Admin_VotingResult_form.setVisible(false);
            Admin_VoterDetails_form.setVisible(true);
            Admin_VoterVerification_form.setVisible(false);

            HomeBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)" );
            CandidatesDetailsBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%) ");
            VotingResultBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");
            VoterVerifybtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");

        }else  if(event.getSource() == VoterVerifybtn) {
            Admin_Home_form.setVisible(false);
            Admin_CandidatesDetails_form.setVisible(false);
            Admin_VotingResult_form.setVisible(false);
            Admin_VoterDetails_form.setVisible(false);
            Admin_VoterVerification_form.setVisible(true);

            HomeBtn.setStyle("-fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)" );
            CandidatesDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%) ");
            VotingResultBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterDetailsBtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 0.0%, #BBDF73 100.0%)");
            VoterVerifybtn.setStyle(" -fx-background-color: linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%) linear-gradient(from 0.0% 0.0% to 100.0% 0.0%, #3CD085 20.0%, #BBDF73 80.0%)");

            VoterDetailsTotalVoter();
            VoterDetailsPendingApproval();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUsername();
        defaultNavigation();

        HomeTotalCandidates();
        HomeElectionStatuslist();
        HomePieChartData();

        candidateShowlist();

        VoterDetailsTotalVoter();
        VoterDetailsPendingApproval();
        VoterDetailsShowList();
        VoterDetails_table.autosizeColumnsOnInitialization();

        VoterVerificationShowListData();
    }
}
