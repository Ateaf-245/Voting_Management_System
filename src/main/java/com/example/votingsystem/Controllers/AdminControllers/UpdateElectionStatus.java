package com.example.votingsystem.Controllers.AdminControllers;

import com.example.votingsystem.Databases.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateElectionStatus {

    private static Connection connect;
    private static PreparedStatement prepare;

    public static void candidateUpdateElectionStatus(int status){
        String sql = "UPDATE candidate SET election_status ="+status+"";
        connect = Database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.executeUpdate();

        }catch (Exception e){e.printStackTrace();}
    }

    public static void  candidateResetElectionStatus(){
        String sql1 = "UPDATE Candidate SET election_status = 0";
        String sql2 = "UPDATE Candidate SET votes = 0";
        String sql3 = "UPDATE voter SET status = 0";

        connect = Database.connectDb();
        try {

            prepare = connect.prepareStatement(sql1);
            prepare.executeUpdate();

            prepare = connect.prepareStatement(sql2);
            prepare.executeUpdate();

            prepare = connect.prepareStatement(sql3);
            prepare.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
