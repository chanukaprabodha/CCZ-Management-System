package lk.ijse.ccz.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ccz.model.*;
import lk.ijse.ccz.model.tm.OrderTm;
import lk.ijse.ccz.reopsitory.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class OrderFormController {

        @FXML
        private Button btnConfirm;

        @FXML
        private Button btnReceipt;

        @FXML
        private Button btnRemove;

        @FXML
        private JFXComboBox<String> cmbCustomerId;

        @FXML
        private TableColumn<?, ?> colPrice;

        @FXML
        private TableColumn<?, ?> colQuantity;

        @FXML
        private TableColumn<?, ?> colRecipe;

        @FXML
        private TextField txtChocolate;

        @FXML
        private TextField txtEssence;

        @FXML
        private TextField txtPacking;

        @FXML
        private TextField txtVanilla;

        @FXML
        private TextField txtcoloring;

        @FXML
        private TextField txtcupcakes;

        @FXML
        private TextField txtCoffe;

        @FXML
        private TextField txtTopper;

        @FXML
        private TextField txtIcing;

        @FXML
        private Label lblOrderDate;

        @FXML
        private Label lblOrderId;

        @FXML
        private Label lblTotal;

        @FXML
        private TextField txtSearchMobile;

        @FXML
        private TableView<OrderTm> tblOrder;

        @FXML
        private Label lblCustomerId;


        private double netTotal;

        private ObservableList<OrderTm> ordertList = FXCollections.observableArrayList();


        public void initialize() {
                setCellValueFactory();
                loadNextOrderId();
                setDate();
        }

        private void setCellValueFactory() {
                colRecipe.setCellValueFactory(new PropertyValueFactory<>("Recipes"));
                colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
                colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        }

        private void setDate() {
                LocalDate now = LocalDate.now();
                lblOrderDate.setText(String.valueOf(now));
        }

        private void loadNextOrderId() {
                try {
                        String currentId = Order_Repo.currentId();
                        String nextId = nextId(currentId);

                        lblOrderId.setText(nextId);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        private String nextId(String currentId) {
                if (currentId != null) {
                        String[] split = currentId.split("O");
                        int id = parseInt(split[1]);    //2
                        return "O" + ++id;

                }
                return "O1";
        }

        private void calculateNetTotal() {
                netTotal = 0;
                for (int i = 0; i < tblOrder.getItems().size(); i++) {
                        netTotal += (double) colPrice.getCellData(i);
                }
                lblTotal.setText(String.valueOf(netTotal));
        }

        @FXML
        void btnSearchOnAction(ActionEvent event) throws SQLException {
                String mobile = txtSearchMobile.getText();
                String cusId = Customer_Repo.findCustomer(mobile);
                lblCustomerId.setText(cusId);
        }

        @FXML
        void btnCoffeeOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i03";
                double qty = Double.parseDouble(txtCoffe.getText());
                allBtnOnAction(ing_id, qty);
                txtCoffe.clear();

        }

        @FXML
        void btnChocolateOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i02";
                double qty = Double.parseDouble(txtChocolate.getText());
                allBtnOnAction(ing_id, qty);
                txtChocolate.clear();
        }

        private void allBtnOnAction(String ing_id, double qty) throws SQLException {

                double unitPrice = Inventory_Repo.getUnitPrice(ing_id);
                double total = unitPrice * qty;

                for (int i = 0; i < tblOrder.getItems().size(); i++) {
                        if (ing_id.equals(colRecipe.getCellData(i))) {
                                qty = qty + ordertList.get(i).getQuantity();
                                total =qty * unitPrice;

                                ordertList.get(i).setQuantity(qty);
                                ordertList.get(i).setPrice(total);

                                tblOrder.refresh();
                                calculateNetTotal();
                                return;
                        }
                }

                OrderTm orderTm = new OrderTm(ing_id, qty, total);

                ordertList.add(orderTm);

                tblOrder.setItems(ordertList);
                calculateNetTotal();
        }

        @FXML
        void btnColoringOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i06";
                double qty = Double.parseDouble(txtcoloring.getText());
                allBtnOnAction(ing_id, qty);
                txtcoloring.clear();
        }

        @FXML
        void btnCupcakeOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i04";
                double qty = Double.parseDouble(txtcupcakes.getText());
                allBtnOnAction(ing_id, qty);
                txtcupcakes.clear();
        }

        @FXML
        void btnEssenceOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i05";
                double qty = Double.parseDouble(txtEssence.getText());
                allBtnOnAction(ing_id, qty);
                txtEssence.clear();

        }

        @FXML
        void btnPackingOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i09";
                double qty = Double.parseDouble(txtPacking.getText());
                allBtnOnAction(ing_id, qty);
                txtPacking.clear();
        }

        @FXML
        void btnVanillaOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i01";
                double qty = Double.parseDouble(txtVanilla.getText());
                allBtnOnAction(ing_id, qty);
                txtVanilla.clear();

        }

        @FXML
        void btnTopperOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i07";
                double qty = Double.parseDouble(txtTopper.getText());
                allBtnOnAction(ing_id, qty);
                txtTopper.clear();

        }

        @FXML
        void btnIcingOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i08";
                double qty = Double.parseDouble(txtIcing.getText());
                allBtnOnAction(ing_id, qty);
                txtIcing.clear();
        }


        @FXML
        void btnConfirmOnAction(ActionEvent event) throws SQLException {

                String orderId = lblOrderId.getText();
                String cusId = lblCustomerId.getText();
                Date date = Date.valueOf(LocalDate.now());
                double totalAmount = Double.parseDouble(lblTotal.getText());

                var order = new Order(orderId, date, cusId, totalAmount);

                List<OrderDetail> odList = new ArrayList<>();

                for (int i = 0; i < tblOrder.getItems().size(); i++) {
                        OrderTm tm = ordertList.get(i);

                        OrderDetail od = new OrderDetail(
                                orderId,
                                (String) colRecipe.getCellData(i),
                                tm.getQuantity(),
                                tm.getPrice()
                        );
                        odList.add(od);
                }

                ConfirmOrder po = new ConfirmOrder(order, odList);

                try {
                        boolean isPlaced = ConfirmOrder_Repo.placeOrder(po);
                        if(isPlaced) {
                                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
                                sendMAil(cusId);
                                tblOrder.getItems().clear();
                                cmbCustomerId.getSelectionModel().clearSelection();
                        } else {
                                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
        }

        private void sendMAil(String cusId) {
                //Customer_Repo.getCustomerEmali(cusId);
        }

        @FXML
        void btnNewCustomerOnAction(ActionEvent event) throws IOException {
                AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

                Scene scene = new Scene(customerPane);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Customer form");

                stage.show();
        }

        @FXML
        void btnReceiptOnAction(ActionEvent event) {

        }

        @FXML
        void btnRemoveOnAction(ActionEvent event) {

        }

        @FXML
        void menuSelectOrder(MouseEvent event) {

        }

}