package lk.ijse.dep10.studentUI.controll;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.dep10.studentUI.db.DBConnection;
import lk.ijse.dep10.studentUI.model.Student;

import java.sql.*;

public class StudentManageViewController {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAddNewStudent;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Student> tblStudents;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {
        tblStudents.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudents.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudents.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        loadStudents();
        txtName.textProperty().addListener((observableValue, s, current) ->{
            txtName.getStyleClass().remove("invalid");
            if (!current.matches("[A-Za-z ]{3,}")) {
                txtName.getStyleClass().add("invalid");
            }
        } );
        txtAddress.textProperty().addListener((observableValue, s, current) ->{
            txtAddress.getStyleClass().remove("invalid");
            if (!current.matches("[A-Za-z ]{3,}")) {
                txtAddress.getStyleClass().add("invalid");
            }
        } );
    }

    private void loadStudents() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT *FROM Student");
            if (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                Student student = new Student(id, name, address);
                tblStudents.getItems().add(student);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load data from the data base please try again").showAndWait();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewStudentOnAction(ActionEvent event) {
        for (Node node:new Node[]{txtAddress,btnSave,txtName}) {
            node.setDisable(false);
            if (node instanceof TextField) {
                ((TextField) node).clear();
                node.requestFocus();
            }
        }
        studentIdGenerate();

    }

    private void studentIdGenerate() {
        int id = tblStudents.getItems().isEmpty() ? 1 : Integer.parseInt(tblStudents.getItems().get(tblStudents.getItems().size() - 1).getId().substring(1))+1;
        txtId.setText(String.format("S%03d", id));
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String name = txtName.getText().strip();
        String address = txtAddress.getText().strip();
        String id = txtId.getText().strip();
        if (!isDataValid(name,address))return;
        Student student = new Student(id, name, address);
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Student(id, name, address) VALUES (?,?,?)");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, address);
            preparedStatement.execute();
            tblStudents.getItems().add(student);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to save the student Please try again").showAndWait();
            throw new RuntimeException(e);
        }
    }

    private boolean isDataValid(String name,String address) {
        if (address.isEmpty() || txtAddress.getStyleClass().contains("invalid")) {
            txtAddress.selectAll();
            txtAddress.requestFocus();
            if(!txtAddress.getStyleClass().contains("invalid")) txtAddress.getStyleClass().add("invalid");
            return false;
        }
        if (name.isEmpty() || txtName.getStyleClass().contains("invalid")) {
            txtName.selectAll();
            txtName.requestFocus();
            if(!txtName.getStyleClass().contains("invalid")) txtName.getStyleClass().add("invalid");
            return false;
        }
        return true;
    }

    @FXML
    void tblStudentsOnKeyReleased(KeyEvent event) {

    }

}
