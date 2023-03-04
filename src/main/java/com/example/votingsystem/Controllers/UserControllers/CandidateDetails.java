package com.example.votingsystem.Controllers.UserControllers;

import com.example.votingsystem.Databases.CandidateData;
import com.example.votingsystem.Databases.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CandidateDetails {


    private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;

    public static ObservableList<CandidateData> dashboardCandidateListData() {
        ObservableList<CandidateData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM candidate";
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            CandidateData candidateData;

            while (result.next()){
                candidateData = new CandidateData(result.getInt("ID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("location"),
                        result.getString("party"),
                        result.getString("photo"),
                        result.getInt("votes"));

                listData.add(candidateData);
            }
        }catch (Exception e){e.printStackTrace();}
        return listData;
    }
}
