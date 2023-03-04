package com.example.votingsystem.Controllers.AdminControllers;

import com.example.votingsystem.Databases.Database;
import com.example.votingsystem.Databases.VoterData;
import com.example.votingsystem.Databases.getData;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class VoterVerifyItem implements Initializable {

    @FXML
    public Label VoterItem_FirstName;

    @FXML
    public Label VoterItem_ID;

    @FXML
    public ImageView VoterItem_Image;

    @FXML
    public Label VoterItem_LastName;

    @FXML
    public Label VoterItem_Location;

    @FXML
    public Label VoterItem_Phone;

    @FXML
    public MFXButton VoterItem_VerifyBtn;

    @FXML
    public HBox VoterItem_form;

    private  Connection connect;
    private  PreparedStatement prepare;

    int  id;

    public void setData(VoterData voterData){

        getData.path = voterData.getImage();
        String url = "file:" +voterData.getImage();

        Image imgProfile = new Image(url,70,90,false,true);
        VoterItem_Image.setImage(imgProfile);

        id = voterData.getVoterID();
        VoterItem_ID.setText(String.valueOf(voterData.getVoterID()));
        VoterItem_FirstName.setText(String.valueOf(voterData.getFirstName()));
        VoterItem_LastName.setText(String.valueOf(voterData.getLastName()));
        VoterItem_Phone.setText(String.valueOf(voterData.getPhone()));
        VoterItem_Location.setText(String.valueOf(voterData.getLocation()));
    }

    public void VerifyVoter(){
        String sql = "UPDATE Voter SET verification = 1 WHERE ID ='"+id+"'";
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
