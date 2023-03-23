package lk.ijse.dep10.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.Util.Employee;
import lk.ijse.dep10.db.DBConnector;

import java.sql.*;

public class EmployeeSceneController {

    public Button btnNew;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Employee emp =tblEmployee.getSelectionModel().getSelectedItem();
            Connection connection =DBConnector.getInstance().getConnection();
            PreparedStatement preStm =connection.prepareStatement("delete from Employee where id =?");
            preStm.setString(1,emp.getId());
            preStm.execute();
            tblEmployee.getItems().remove(emp);

            tblEmployee.getSelectionModel().clearSelection();
            btnNew.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to delete from database");
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            if(!validate()) return;
            String id = txtID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();

            Connection connection = DBConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preStm = connection.prepareStatement("Insert into Employee value (?,?,?)");

            preStm.setString(1,id);
            preStm.setString(2,name);
            preStm.setString(3,address);
            preStm.execute();

            Employee employee = new Employee(id, name, address);
            tblEmployee.getItems().add(employee);
            btnNew.fire();

            connection.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            try {
                DBConnector.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }


    }
    public void initialize(){
        btnDelete.setDisable(true);
        updateTable();
        generateId();


        tblEmployee.getSelectionModel().selectedItemProperty().addListener((value,previous,current)->{
            btnDelete.setDisable(current==null);
            if(current!=null){
                txtID.setText(current.getId());
                txtName.setText(current.getName());
                txtAddress.setText(current.getAddress());
            }

        });
        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
    }
    public boolean validate(){
        boolean validate = true;
        if(!txtAddress.getText().matches(".{5,}")){
            txtAddress.requestFocus();
            validate=false;

        }
        if(!txtName.getText().strip().matches("[A-z ]{5,}")){
            txtName.requestFocus();
            validate=false;
        }
        return validate;
    }
    private void generateId(){

        if(tblEmployee.getItems().isEmpty()){
            txtID.setText("E001");
        }
        else {
            ObservableList<Employee >empList= tblEmployee.getItems();
            txtID.setText(String.format("E%03d",Integer.parseInt(empList.get(empList.size()-1).getId().substring(1))+1));
        }

    }
    private void updateTable(){
        Connection connection =DBConnector.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from Employee");
            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                Employee employee = new Employee(id, name, address);
                tblEmployee.getItems().add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtName.clear();
        txtAddress.clear();
        generateId();
    }
}
