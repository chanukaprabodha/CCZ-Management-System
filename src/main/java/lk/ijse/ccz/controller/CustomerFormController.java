package lk.ijse.ccz.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ccz.model.Customer;
import lk.ijse.ccz.model.tm.CustomerTm;
import lk.ijse.ccz.reopsitory.Customer_Repo;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ccz.util.Regex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCustomerID;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane customerPane;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    private List<Customer> customerList = new ArrayList<>();

    public void initialize() {
        this.customerList = getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
    }

    private List<Customer> getAllCustomers() {
        List<Customer> customerList = null;
        try {
            customerList = Customer_Repo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = Customer_Repo.getAll();
            for (Customer customer : customerList) {
                CustomerTm customerTm = new CustomerTm(
                        customer.getCustomerID(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getAddress(),
                        customer.getContact()
                );

                tmList.add(customerTm);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblCustomer.setItems(tmList);
    }

    private void setCellValueFactory() {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    @FXML
    void btnCusAddOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        Customer customer = new Customer(id, name, email,address, contact);

        if (isValid()) {
            try {
                boolean isSaved = Customer_Repo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        loadCustomerTable();
    }

    @FXML
    void btnCusClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnCusDeleteOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();

        try {
            boolean isDeleted = Customer_Repo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e);
        }
        loadCustomerTable();
    }

    @FXML
    void btnCusUpdateOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        Customer customer = new Customer(id, name,email , address, contact);

        if (isValid()) {
            try {
                boolean isUpdated = Customer_Repo.update(customer);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        loadCustomerTable();
    }

    @FXML
    void cusTableOnClick(MouseEvent event) {
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        txtCustomerId.setText(selectedItem.getCustomerID());
        txtName.setText(selectedItem.getName());
        txtEmail.setText(selectedItem.getEmail());
        txtAddress.setText(selectedItem.getAddress());
        txtContact.setText(selectedItem.getContact());
    }

    @FXML
    void CustomerIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.Cid, txtCustomerId);

    }

    @FXML
    void addressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.ADDRESS, txtAddress);
    }

    @FXML
    void contactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.CONTACT, txtContact);
    }

    @FXML
    void emailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.EMAIL, txtEmail);
    }

    @FXML
    void nameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.NAME, txtName);
    }

    public boolean isValid() {
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.EMAIL,txtEmail)) {
            return false;
        }
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.Cid,txtCustomerId)) {
            return false;
        }
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.CONTACT,txtContact)) {
            return false;
        }
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.NAME,txtName)) {
            return false;
        }
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.ADDRESS,txtAddress)) {
            return false;
        }
        return true;
    }

}
