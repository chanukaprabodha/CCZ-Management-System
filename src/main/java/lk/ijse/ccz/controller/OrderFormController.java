package lk.ijse.ccz.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.model.*;
import lk.ijse.ccz.model.tm.OrderTm;
import lk.ijse.ccz.reopsitory.*;
import lk.ijse.ccz.util.SendMail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static lk.ijse.ccz.reopsitory.Order_Repo.customerChart;
import static lk.ijse.ccz.reopsitory.Order_Repo.incomeChart;
import static lk.ijse.ccz.util.SendMail.customerMailAddress;
import static lk.ijse.ccz.util.SendMail.customerName;

public class OrderFormController {

        @FXML
        private Button btnTopper;

        @FXML
        private Button btnVanilla;

        @FXML
        private Button btnCupCake;

        @FXML
        private Button btnEssence;

        @FXML
        private Button btnIcing;

        @FXML
        private Button btnPacking;

        @FXML
        private Button btnChocolate;

        @FXML
        private Button btnCoffee;

        @FXML
        private Button btnColoring;

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
                Platform.runLater(() -> txtSearchMobile.requestFocus());


                List<Node> buttons = Arrays.asList(
                        txtCoffe, btnCoffee,
                        txtChocolate, btnChocolate,
                        txtcoloring, btnColoring,
                        txtcupcakes, btnCupCake,
                        txtEssence, btnEssence,
                        txtPacking, btnPacking,
                        txtVanilla, btnVanilla,
                        txtTopper, btnTopper,
                        txtIcing, btnIcing
                );
                buttons.forEach(node -> node.setDisable(true));

                // Add a listener to the search field
                txtSearchMobile.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue.matches("\\d{10}")) {
                                // Enable buttons if a valid contact number is entered
                                buttons.forEach(node -> node.setDisable(false));
                        } else {
                                // Disable buttons if the search field is empty or contains an invalid contact number
                                buttons.forEach(node -> node.setDisable(true));
                        }
                });
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
                        String regex = "^\\D*(\\d+)$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(currentId);
                        int id = 1;
                        if (matcher.find()) {
                                id = Integer.parseInt(matcher.group(1)) + 1;
                        }
                        return "O" + String.format("%04d", id);
                }
                return "O0001";
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
                if (cusId != null){
                        lblCustomerId.setText(cusId);
                }else {
                        new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
                }
        }

        @FXML
        void btnCoffeeOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i003";
                double qty = Double.parseDouble(txtCoffe.getText());
                allBtnOnAction(ing_id, qty);
                txtCoffe.clear();

        }

        @FXML
        void btnChocolateOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i002";
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
                String ing_id = "i006";
                double qty = Double.parseDouble(txtcoloring.getText());
                allBtnOnAction(ing_id, qty);
                txtcoloring.clear();
        }

        @FXML
        void btnCupcakeOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i004";
                double qty = Double.parseDouble(txtcupcakes.getText());
                allBtnOnAction(ing_id, qty);
                txtcupcakes.clear();
        }

        @FXML
        void btnEssenceOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i005";
                double qty = Double.parseDouble(txtEssence.getText());
                allBtnOnAction(ing_id, qty);
                txtEssence.clear();

        }

        @FXML
        void btnPackingOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i009";
                double qty = Double.parseDouble(txtPacking.getText());
                allBtnOnAction(ing_id, qty);
                txtPacking.clear();
        }

        @FXML
        void btnVanillaOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i001";
                double qty = Double.parseDouble(txtVanilla.getText());
                allBtnOnAction(ing_id, qty);
                txtVanilla.clear();

        }

        @FXML
        void btnTopperOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i007";
                double qty = Double.parseDouble(txtTopper.getText());
                allBtnOnAction(ing_id, qty);
                txtTopper.clear();

        }

        @FXML
        void btnIcingOnAction(ActionEvent event) throws SQLException {
                String ing_id = "i008";
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

                customerName = Customer_Repo.getCustomerName(cusId);

                customerMailAddress = Customer_Repo.getCustomerEmail(cusId);

                ConfirmOrder po = new ConfirmOrder(order, odList);

                try {
                        boolean isPlaced = ConfirmOrder_Repo.placeOrder(po);
                        if (isPlaced) {
                                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
                                tblOrder.getItems().clear();
                                lblCustomerId.setText("");
                                txtSearchMobile.clear();
                                lblTotal.setText("0");
                                loadNextOrderId();
                                
                                SendMail sendMail = new SendMail();

                                sendMail.sendMail("Chamu Cake Zone Order Confirmation", "Hi " + customerName + ",\n\n" +
                                        "\tThank you for shopping with us. " +
                                        "\tYour order is confirmed. " +
                                        "\tWe'll let you know when your order is ready\n" +
                                        "\n\tOrder Details:\n\n" +
                                        "\t\t✅  Placed on :  " + date + "\n\n" +
                                        "\t\t✅  Order ID :  " + orderId + "\n\n" +
                                        "\t\t✅  Total Amount :  " + totalAmount + "\n" +
                                        "\n\n\tSent with ❤️ from CCZ.\n\n");
                        } else {
                                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
                        }
                } catch(Exception e){
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
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
        void btnprintLableOnAction(ActionEvent event) throws JRException, SQLException {
                JasperDesign jasperDesign =
                        JRXmlLoader.load("src/main/resources/Report/kitchenLabel.jrxml");
                JasperReport jasperReport =
                        JasperCompileManager.compileReport(jasperDesign);

                String orderId = Order_Repo.getMostRecentOrderId();

                Map<String, Object> parameter = new HashMap<>();
                parameter.put("orderId", orderId);

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parameter,
                                DbConnection.getInstance().getConnection());

                JasperViewer.viewReport(jasperPrint,false);

        }

        @FXML
        void btnRemoveOnAction(ActionEvent event) {
                tblOrder.getItems().clear();
                lblTotal.setText("0");
        }

        @FXML
        void menuSelectOrder(MouseEvent event) {

        }

        @FXML
        void txtSearchFieldOnAction(ActionEvent event) throws SQLException {
                btnSearchOnAction(event);
        }

}
