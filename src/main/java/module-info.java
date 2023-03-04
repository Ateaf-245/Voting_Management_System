module com.example.votingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.votingsystem to javafx.fxml;
    exports com.example.votingsystem;
    exports com.example.votingsystem.Controllers;
    exports com.example.votingsystem.Controllers.UserControllers;
    exports com.example.votingsystem.Controllers.AdminControllers;
    exports com.example.votingsystem.Databases;
    opens com.example.votingsystem.Controllers to javafx.fxml;
}