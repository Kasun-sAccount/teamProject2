package lk.ijse.dep10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//<<<<<<< HEAD
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.dep10.db.DBConnector;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
//=======
import javafx.stage.Stage;

import java.io.IOException;
//>>>>>>> refs/remotes/origin/main

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
//<<<<<<< HEAD
    public void start(Stage primaryStage) {
        checkTable();
        try {
            primaryStage.setScene(new Scene(new FXMLLoader(getClass().getResource("/View/EmployeeScene.fxml")).load()));
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load UI").showAndWait();
            throw new RuntimeException(e);
        }
    }
    public void checkTable() {
        Connection connection = DBConnector.getInstance().getConnection();
        try {
            InputStream is = getClass().getResourceAsStream("/schema.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");

            }
            br.close();
//            System.out.println(sb.toString());
            Statement statement = connection.createStatement();
            statement.execute(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to connect with schema file").showAndWait();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to connect with database server").showAndWait();
            throw new RuntimeException(e);
        }
    }
//=======
//    public void start(Stage primaryStage) {
//        try {
//            primaryStage.setScene(new Scene(new FXMLLoader(getClass().getResource("/View/MainScene.fxml")).load()));
//            primaryStage.show();
//            primaryStage.centerOnScreen();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
////>>>>>>> refs/remotes/origin/main
//    }
}
