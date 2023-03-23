package lk.ijse.dep10.studentUI.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() {
        try {

            File file = new File("application.properties");
            Properties properties = new Properties();
            FileReader fr = new FileReader(file);
            String host = properties.getProperty("dep10.host","dep10.lk");
            String port = properties.getProperty("dep10.port","3306");
            String database = properties.getProperty("dep10.database","dep10_git");
            String user = properties.getProperty("dep10.user","root");
            String password = properties.getProperty("dep10.password","");
            properties.load(fr);
            String connectionLink = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
            connection = DriverManager.getConnection(connectionLink, "root", "root");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to connect with database").showAndWait();
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Application properties not found contact the technical team").showAndWait();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        return dbConnection == null ? dbConnection = new DBConnection() : dbConnection;
    }
}
