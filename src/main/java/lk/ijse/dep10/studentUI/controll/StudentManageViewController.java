package lk.ijse.dep10.studentUI.controll;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class StudentManageViewController {

    @FXML
    private Button brnSave;

    @FXML
    private Button btnAddNewStudent;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<?> tblStudents;

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
    }

    private void loadStudents() {
    }

    @FXML
    void btnAddNewStudentOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void tblStudentsOnKeyReleased(KeyEvent event) {

    }

}
