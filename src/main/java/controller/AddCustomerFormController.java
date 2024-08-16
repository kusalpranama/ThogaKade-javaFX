package controller;

import com.jfoenix.controls.JFXTextField;
import db.ThogakadePOS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Customer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private ComboBox<String> comboTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() ||
                txtNumber.getText().isEmpty() || comboTitle.getValue() == null || dateDob.getValue() == null) {
            System.out.println("Please fill all the fields");
            infoBox1();
            return;
        }


        List<Customer> customerList = ThogakadePOS.getInstance().getConnection();
        boolean idExists = customerList.stream().anyMatch(customer -> customer.getId().equals(txtId.getText()));

        if (idExists) {
            System.out.println("Customer ID already exists.");
            infoBox2();
            return;
        }


        customerList.add(
                new Customer(
                        txtId.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        txtNumber.getText(),
                        comboTitle.getValue(),
                        dateDob.getValue()));
        System.out.println("Customer added: " + customerList);
        infoBox3();

        clearFields();
    }


    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtNumber.clear();
        comboTitle.setValue(null);
        dateDob.setValue(null);
    }
    public static void infoBox1()
    {
        JOptionPane.showMessageDialog(null,  "Please fill all the fields.: ","ERROR", JOptionPane.ERROR_MESSAGE);


    }public static void infoBox3()
    {
        JOptionPane.showMessageDialog(null,  "Customer added😍.: ");


    }
    public static void infoBox2()
    {
        JOptionPane.showMessageDialog(null,  "Customer ID already exists..: ","ERROR", JOptionPane.ERROR_MESSAGE);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("MR.");
        titles.add("Miss.");
        comboTitle.setItems(titles);


    }

    public void btnMainPageOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtId.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dash_board_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

}
