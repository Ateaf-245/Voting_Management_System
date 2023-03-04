package com.example.votingsystem.Controllers.AdminControllers;

import com.example.votingsystem.Databases.Database;
import com.example.votingsystem.Databases.getAlert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginController {

    private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;

    public static Boolean login(String username, String password){
        String sql = "SELECT * FROM admin WHERE admin_id = ? AND password = ?";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,username);
            prepare.setString(2,password);

            result = prepare.executeQuery();
            if (result.next()){
                getAlert.alertSuccessfulLogin();
                return true;
            }else {
                getAlert.alertIncorrectCredentials();
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }
}
