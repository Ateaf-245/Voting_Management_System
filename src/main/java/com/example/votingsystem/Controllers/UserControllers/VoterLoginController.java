package com.example.votingsystem.Controllers.UserControllers;

import com.example.votingsystem.Databases.Database;
import com.example.votingsystem.Databases.getAlert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VoterLoginController {

    private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;

    /**
     * This Method connects to Database and verify Voter credentials.
     * @param username contains Voter Id.
     * @param password contains Voter's password.
     * @return true if user credentials are correct and false of user credentials are invalid to LoginController Method.
     */
    public static Boolean login(String username, String password){

        String sql = "SELECT * FROM voter WHERE id = ? AND password = ?";
        connect = Database.connectDb();

        try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, username);
                prepare.setString(2, password);

                result = prepare.executeQuery();

                if (result.next()) {

                    String check = "SELECT verification FROM voter WHERE id = ?";

                    prepare = connect.prepareStatement(check);
                    prepare.setString(1, username);
                    result = prepare.executeQuery();
                    int verify = 0;

                    while (result.next()) {
                        verify = result.getInt("verification");
                    }

                    if (verify == 0) {
                        getAlert.alertVerificationPending();
                        return false;
                    }else {
                        getAlert.alertSuccessfulLogin();
                        return true;
                    }
                } else {
                    getAlert.alertIncorrectCredentials();
                }

        }catch(Exception e){
                e.printStackTrace();
        }
        return false;
    }
}
