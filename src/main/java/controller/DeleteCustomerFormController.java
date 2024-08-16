package controller;

import com.jfoenix.controls.JFXTextField;
import db.ThogakadePOS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Customer;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.*;
import java.util.List;

public class DeleteCustomerFormController {

    @FXML
    private JFXTextField txtId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
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


        Customer customerToRemove = null;

        for (Customer customer : customerList) {
            if (customer != null && customer.getId().equals(customerId)) {
                customerToRemove = customer;
                break;
            }
        }

        if (customerToRemove != null) {
            customerList.remove(customerToRemove);
            infoBox1();
            //clearFormFields(); // Clear the input field after deletion
        } else {
            infoBox3();
        }
    }
    public static void infoBox1()
    {
        JOptionPane.showMessageDialog(null,  "Customer removed successfully..: ");


    }
    public static void infoBox2()
    {
        JOptionPane.showMessageDialog(null,  "Please enter a valid Customer ID.: ","ERROR", JOptionPane.ERROR_MESSAGE);

    }
    public static void infoBox3()
    {
        JOptionPane.showMessageDialog(null,  "Customer not found..: ","ERROR", JOptionPane.ERROR_MESSAGE);

    }

    private void clearFormFields() {
        txtId.clear();
    }
}
