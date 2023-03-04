package com.example.votingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Voting Management System
 * <p>Thanks for the support guys!</p>
 * @author Ateaf
 * @version  1.0.0
 */

/**
 * This Class call the Login.fxml file
 */
public class App extends Application {

    private double X = 0;
    private double Y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root  = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);

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

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        launch(args);
    }
}