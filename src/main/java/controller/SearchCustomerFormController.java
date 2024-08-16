package controller;

import com.jfoenix.controls.JFXTextField;
import db.ThogakadePOS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import model.Customer;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.*;
import java.util.List;

public class SearchCustomerFormController {

    public JFXTextField txtNmuber;
    @FXML
    private DatePicker dateDob;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;



    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String customerId = txtId.getText();

        if (customerId == null || customerId.isEmpty()) {
            infoBox2();
            return;
        }


        List<Customer> customerList = ThogakadePOS.getInstance().getConnection();

        if (customerList == null) {
            System.out.println("Error retrieving customers from database.");
            return;
        }

        Customer foundCustomer = null;


        for (Customer customer : customerList) {
            if (customer != null && customer.getId().equals(customerId)) {
                foundCustomer = customer;
                break;
            }
        }


        if (foundCustomer != null) {
            txtName.setText(foundCustomer.getName());
            txtAddress.setText(foundCustomer.getAddress());
           txtNmuber.setText(foundCustomer.getNumber());


            if (foundCustomer.getDate() != null) {
                dateDob.setValue(foundCustomer.getDate());
            } else {
                System.out.println("Customer's date of birth is null.");
                dateDob.setValue(null);
            }
        } else {

            infoBox1();
            //clearFormFields();
        }
    }
    public static void infoBox1()
    {
        JOptionPane.showMessageDialog(null,  "Customer not found.: ","ERROR", JOptionPane.ERROR_MESSAGE);


    }
    public static void infoBox2()
    {
        JOptionPane.showMessageDialog(null,  "Please enter a valid Customer ID.: ","ERROR", JOptionPane.ERROR_MESSAGE);

    }

    private void clearFormFields() {
        txtName.clear();
        txtAddress.clear();
        txtNmuber.clear();
        dateDob.setValue(null);
    }


}
