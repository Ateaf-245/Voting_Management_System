package com.example.votingsystem.Databases;
;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.example.votingsystem.Databases.getAlert.alertErrorDatabaseConnection;

public class Database {
    public static Connection connectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/votingmanagementsystem","root","");
            return connect;
        }catch (Exception e){
            alertErrorDatabaseConnection();

        }
        return null;
    }
}
