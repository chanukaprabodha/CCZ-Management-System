package lk.ijse.ccz.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ccz.reopsitory.Customer_Repo;
import lk.ijse.ccz.reopsitory.Order_Repo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;


public class DashboardFormController {

    @FXML
    private AnchorPane centerNode;

    @FXML
    private BarChart<?, ?> customerChart;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<?, ?> incomeChart;

    @FXML
    private Label lblNumOfCustomer;

    @FXML
    private Label lblNumOfTodayOrders;

    @FXML
    private Label lblTodayIncome;

    @FXML
    private Label lblTotalIncome;

    @FXML
    private Label lblUsername;

    private void incomeChart(){
        incomeChart.getData().clear();

        XYChart.Series chart = new XYChart.Series();

        XYChart.Series series = Order_Repo.incomeChart(chart);

        incomeChart.getData().add(series);
    }

    private void customerChart(){
        customerChart.getData().clear();

        XYChart.Series chart = new XYChart.Series();

        XYChart.Series series = Order_Repo.customerChart(chart);

        customerChart.getData().add(series);
    }

    private void lblNumOfCustomer() throws SQLException {
        lblNumOfCustomer.setText(String.valueOf(Customer_Repo.getCustomerCount()));
    }

    private void lblTodayIncome() throws SQLException {
        Date date = new Date();

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        double todayIncome = Order_Repo.getTodayIncome(sqlDate);

        lblTodayIncome.setText(String.valueOf("Rs. " + todayIncome));
    }

    private void lblTotalIncome() throws SQLException {
        double totalIncome = Order_Repo.getTotalIncome();
        lblTotalIncome.setText(String.valueOf("Rs. " + totalIncome));
    }

    private void lblNumOfTodayOrders() throws SQLException {
        Date date = new Date();

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        int ordersCount = Order_Repo.getTodayOrder(sqlDate);

        lblNumOfTodayOrders.setText(String.valueOf(ordersCount));
    }

    public void initialize() throws SQLException {
        setUserName();
        incomeChart();
        customerChart();
        lblNumOfCustomer();
        lblTodayIncome();
        lblTotalIncome();
        lblNumOfTodayOrders();
    }

    private void setUserName() {
       String userName =  LoginFormController.getUserName(LoginFormController.userId);
       lblUsername.setText(userName);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(customerPane);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.75), this.centerNode);
        transition.setFromX(this.centerNode.getScene().getWidth());
        transition.setToX(0);
        transition.play();

    }

    @FXML
    void btnEmployeesOnAction(ActionEvent event) throws IOException {
        AnchorPane employeePane = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));

        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(employeePane);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.75), this.centerNode);
        transition.setFromX(this.centerNode.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) throws IOException {
        AnchorPane inventoryPane = FXMLLoader.load(this.getClass().getResource("/view/inventory_form.fxml"));

        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(inventoryPane);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.75), this.centerNode);
        transition.setFromX(this.centerNode.getScene().getWidth());
        transition.setToX(0);
        transition.play();

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {

        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to Logout?", yes, no).showAndWait();

        if(type.orElse(no) == yes) {
            StackPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) this.centerNode.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Login Form");
        }

    }

    @FXML
    void btnMenuOnAction(ActionEvent event) throws IOException{
        AnchorPane menuPane = FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml"));

        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(menuPane);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.75), this.centerNode);
        transition.setFromX(this.centerNode.getScene().getWidth());
        transition.setToX(0);
        transition.play();

    }

    @FXML
    void dashBoardOnAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(dashboard_form);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.75), this.centerNode);
        transition.setFromX(this.centerNode.getScene().getWidth());
        transition.setToX(0);
        transition.play();
        incomeChart();
        customerChart();
        lblNumOfCustomer();
        lblTodayIncome();
        lblTotalIncome();
        lblNumOfTodayOrders();

    }

    @FXML
    void emailClickOnAction(MouseEvent event) throws IOException {
        Desktop.getDesktop().browse(URI.create("https://mail.google.com/mail/u/0/#inbox"));
    }

    @FXML
    void facebookClickOnAction(MouseEvent event) throws IOException {
        Desktop.getDesktop().browse(URI.create("https://web.facebook.com/chamuscakeclub"));
    }

    @FXML
    void whatsappClickOnAction(MouseEvent event) throws IOException {
        Desktop.getDesktop().browse(URI.create("https://wa.me/0763055539"));
    }
}

