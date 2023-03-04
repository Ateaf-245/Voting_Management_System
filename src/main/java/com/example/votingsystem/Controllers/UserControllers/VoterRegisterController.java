package com.example.votingsystem.Controllers.UserControllers;

import com.example.votingsystem.Databases.Database;
import com.example.votingsystem.Databases.getAlert;
import com.example.votingsystem.Databases.getData;
import com.example.votingsystem.Databases.getImageMoved;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;

public class VoterRegisterController implements Initializable {

    @FXML
    public AnchorPane Register_form;

    @FXML
    public MFXTextField Voter_Register_FirstName;

    @FXML
    public ImageView Voter_Register_Image;

    @FXML
    public MFXButton Voter_Register_ImageUploadBtn;

    @FXML
    public MFXTextField Voter_Register_LastName;

    @FXML
    public MFXTextField Voter_Register_Location;

    @FXML
    public Hyperlink Voter_Register_LoginLink;

    @FXML
    public MFXTextField Voter_Register_Phone;

    @FXML
    public MFXPasswordField Voter_Register_SetPassword;

    @FXML
    public MFXPasswordField Voter_Register_ConfirmPassword;

    @FXML
    public MFXButton Voter_Register_Submit;

    private double X = 0;
    private double Y = 0;

    private  Connection connect;
    private  PreparedStatement prepare;

    Image image;
    File file;
    String rootDirectory;
    String destDirectory;

    /**
     * This Method shows up dialog box to select Image and after user selecting the Image it shows the Selected Image in Register form with specified size.
     */
    public void registerUploadImage(){
//        this 2 line is to get file chooser window for user to select
        FileChooser open =new FileChooser();
        file = open.showOpenDialog(Register_form.getScene().getWindow());

        if(file!=null) {

            rootDirectory = file.getParent()+"\\"+file.getName();
            destDirectory = "../VotingSystem/src/main/resources/Images/Voters/";

            getData.path = rootDirectory;

            image = new Image(file.toURI().toString(),125,150,false,true);
            Voter_Register_Image.setImage(image);
        }
    }

    /**
     * This Method creates a voter account and submit it for Admin approval
     */
    public void registerSubmit(){

        String sql ="INSERT INTO voter (firstName, lastName, mobile, password, location, photo, status, verification)"
                +" VALUES (?,?,?,?,?,?,0,0)";

        connect = Database.connectDb();

        try {

            if (Voter_Register_FirstName.getText().isEmpty() || Voter_Register_LastName.getText().isEmpty() ||
                    Voter_Register_Phone.getText().isEmpty() || Voter_Register_Location.getText().isEmpty() ||
                    Voter_Register_SetPassword.getText().isEmpty() || Voter_Register_ConfirmPassword.getText().isEmpty()) {

                getAlert.alertErrorText();

            }else if(!Objects.equals(Voter_Register_SetPassword.getText(), Voter_Register_ConfirmPassword.getText())){

                getAlert.alertErrorPassword();

            }else if(getData.path == null || getData.path.equals("")){

                getAlert.alertErrorImage();

            } else {

//               coping the image from user selected location to Images folder
                getImageMoved.ImageMover(rootDirectory,destDirectory,file);

                prepare = connect.prepareStatement(sql);
                prepare.setString(1,Voter_Register_FirstName.getText());
                prepare.setString(2,Voter_Register_LastName.getText());
                prepare.setString(3,Voter_Register_Phone.getText());
                prepare.setString(4,Voter_Register_SetPassword.getText());
                prepare.setString(5,Voter_Register_Location.getText());
                prepare.setString(6,getData.path);
                prepare.executeUpdate();

                String Voter_ID = "SELECT ID from Voter WHERE (firstName, lastName, mobile, password, location, photo, status, verification)"+
                                " VALUES ("+Voter_Register_FirstName.getText()+","+Voter_Register_LastName.getText()+
                                ","+Voter_Register_Phone.getText()+","+Voter_Register_SetPassword.getText()+
                                ","+Voter_Register_Location.getText()+","+getData.path+",0,0)";

                getAlert.alertRegisterInformation(Voter_ID);
                registerLoginLink();
            }
        } catch(Exception e){e.printStackTrace();}
    }

    /**
     * This Method redirect user to Login Page.
     * @throws IOException
     */
    public void registerLoginLink() throws IOException {
        Voter_Register_LoginLink.getScene().getWindow().hide();
        Parent root  = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent event) ->{
            X = event.getSceneX();
            Y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event )->{
            stage.setX(event.getScreenX() - X);
            stage.setY(event.getScreenY() - Y);
        });

        stage.setTitle("Voting Management System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
