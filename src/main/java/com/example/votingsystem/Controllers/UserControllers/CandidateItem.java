package com.example.votingsystem.Controllers.UserControllers;

import com.example.votingsystem.Databases.CandidateData;
import com.example.votingsystem.Databases.Database;
import com.example.votingsystem.Databases.getData;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class CandidateItem implements Initializable {

    @FXML
    public Label CandidateItem_FirstName;

    @FXML
    public ImageView CandidateItem_Image;

    @FXML
    public Label CandidateItem_LastName;

    @FXML
    public Label CandidateItem_Location;

    @FXML
    public Label CandidateItem_Party;

    @FXML
    public Label CandidateItem_SNo;

    @FXML
    public MFXButton CandidateItem_VoteBtn;

    @FXML
    public HBox CandidateItem__form;

    private Connection connect;
    private PreparedStatement prepare;

    int id;

    /**
     * This Method shows each candidate details to user in HBox form with a button to vote.
     * @param candidateData contains candidates data like First Name, Last Name, Party, Location, etc.
     */
    public void setData(CandidateData candidateData){
        getData.path = candidateData.getImage();
        String url =  "file:"+candidateData.getImage();

        Image imgProfile = new Image(url,70,90,false,true);
        CandidateItem_Image.setImage(imgProfile);

        id = candidateData.getCandidate_Id();
        CandidateItem_FirstName.setText(String.valueOf(candidateData.getFirstName()));
        CandidateItem_LastName.setText(String.valueOf(candidateData.getLastName()));
        CandidateItem_Location.setText(String.valueOf(candidateData.getLocation()));
        CandidateItem_Party.setText(String.valueOf(candidateData.getParty()));
    }

    /**
     * This Method is executed when user click on Vote Button, and it increments the votes count of the particular Candidate by 1.
     */
    public void candidateVote(){
        String sql = "UPDATE candidate SET votes = votes + 1 WHERE ID ='"+id+"'";
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
