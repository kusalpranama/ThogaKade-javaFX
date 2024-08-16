package controller;

import com.jfoenix.controls.JFXTextField;
import db.ThogakadePOS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateCustomerFormController implements Initializable {

    public Label txtID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtNumber;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableView<Customer> tblCustomers;
    private void setTextToValues(Customer customer) {
        if (customer != null) {
            txtID.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtNumber.setText(customer.getNumber());
        } else {
            System.out.println("Selected customer is null");
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("date"));


        btnReloadOnAction(null);


        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            } else {
                System.out.println("Selected customer is null");
            }
        });
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerID = txtID.getText();

        if (customerID == null || customerID.isEmpty()) {
            System.out.println("No customer selected.");
            return;
        }

        // Get the list of customers
        List<Customer> customerList = ThogakadePOS.getInstance().getConnection();

        if (customerList == null) {
            infoBox2();
            return;
        }


        int indexToUpdate = -1;
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId().equals(customerID)) {
                indexToUpdate = i;
                break;
            }
        }

        if (indexToUpdate >= 0) {

            Customer updatedCustomer = customerList.get(indexToUpdate);
            updatedCustomer.setName(txtName.getText());
            updatedCustomer.setAddress(txtAddress.getText());
            updatedCustomer.setNumber(txtNumber.getText());


            customerList.set(indexToUpdate, updatedCustomer);
            infoBox1();
            System.out.println("Customer updated: " + updatedCustomer);


            btnReloadOnAction(actionEvent);


            tblCustomers.getSelectionModel().select(updatedCustomer);


            clearTextFields();
        } else {
            System.out.println("Customer ID not found.");
            infoBox3();
        }
        tblCustomers.refresh();
    }

    private void clearTextFields() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNumber.setText("");
    }
    public static void infoBox1()
    {
        JOptionPane.showMessageDialog(null,  "Customer updated..: ");


    }
    public static void infoBox2()
    {
        JOptionPane.showMessageDialog(null,  "Customer list is null!.: ","ERROR", JOptionPane.ERROR_MESSAGE);

    }
    public static void infoBox3()
    {
        JOptionPane.showMessageDialog(null,  "Customer ID not found..: ","ERROR", JOptionPane.ERROR_MESSAGE);

    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        List<Customer> customerList = ThogakadePOS.getInstance().getConnection();
        if (customerList != null) {
            ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(customerList);
            tblCustomers.setItems(customerObservableList);
        } else {
            System.out.println("Customer list is null");
        }
    }


}
