package lk.ijse.dep10.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private static DBConnector dbConnector;
    private final Connection connection;
    private DBConnector(){
        try {
            Properties properties = new Properties();
            File file = new File("application.properties");
            FileReader fileReader = new FileReader(file);
            properties.load(fileReader);
            fileReader.close();

            String host = properties.getProperty("dep.host");
            String port = properties.getProperty("dep.port");
            String database = properties.getProperty("dep.database");
            String user = properties.getProperty("dep.user");
            String password = properties.getProperty("dep.password");

            StringBuilder sb = new StringBuilder("jdbc:mysql://");
            sb.append(host).append(":").append(port).append("/").append(database).append("?createDatabaseIfNotExist=true&allowMultiQueries=true");

            connection = DriverManager.getConnection(sb.toString(),user,password);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to read data source bundle").showAndWait();
            throw new RuntimeException(e);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to connect with data base").showAndWait();
            throw new RuntimeException(e);
        }


    }
    public static DBConnector getInstance(){
        return (dbConnector==null)?dbConnector=new DBConnector():dbConnector;


    }
    public Connection getConnection(){
        return connection;
    }

}
