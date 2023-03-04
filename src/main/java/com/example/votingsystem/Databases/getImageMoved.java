package com.example.votingsystem.Databases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class getImageMoved {
    public  static void ImageMover(String rootDirectory, String destDirectory, File file){

        try {
            FileInputStream inputStream = new FileInputStream(rootDirectory);
            FileOutputStream outputStream = new FileOutputStream(destDirectory + file.getName());

            int data = inputStream.read();
            while (data != -1) {
                outputStream.write(data);
                data = inputStream.read();
            }
            inputStream.close();
            outputStream.close();

            getData.path = destDirectory + file.getName();

        } catch (Exception e){e.printStackTrace();}
    }
}
