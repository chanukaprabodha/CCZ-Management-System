package lk.ijse.ccz.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private StackPane rootNode;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_userId;

    @FXML
    private Button side_AlreadyHave;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_EmployeeId;

    @FXML
    private PasswordField su_password;

    @FXML
    private TextField su_userID;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    public static String userId;

    public static String getUserName(String userId) {
        String sql = "select e.name from employee e join cardinality c on e.employeeId = c.employeeID where userId = ?";

        String userName = null;
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, userId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                userName = resultSet.getString(1);
                return userName;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Try Again...!").show();
        }
        return userName;
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String employeeId = su_EmployeeId.getText();
        String userId = su_userID.getText();
        String pw = su_password.getText();

        saveUser(employeeId, userId, pw);

    }

    private void saveUser(String employeeId, String userId, String pw) {

        if (isValid()) {
            String sql = "INSERT INTO cardinality VALUES (?, ?, ?)";


            try {
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setObject(1, employeeId);
                pstm.setObject(2, userId);
                pstm.setObject(3, pw);
                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION,"User Saved...!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Try Again...!").show();
            }
        }else {
            System.out.println("not saved");
        }
    }

    @FXML
    void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();
        

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
                si_loginForm.setVisible(true);
            });

            slider.play();
        } else if (event.getSource() == side_AlreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);
                si_loginForm.setVisible(true);
            });

            slider.play();
        }

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        userId = si_userId.getText();

        String pw = si_password.getText();

        try {
            checkLogin(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void checkLogin(String userId, String pw) throws IOException, SQLException {
        String sql = "SELECT userID,Password FROM cardinality WHERE userId = ?";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, userId);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String dbpw = resultSet.getString(2);
                if (dbpw.equals(pw)) {
                    navigateToTheDashboard();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Password is incorrect...!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User Id not found...!").show();
            }
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void PasswordFieldOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        si_password.requestFocus();
    }

    @FXML
    void employeeIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.Eid,su_EmployeeId);
    }

    @FXML
    void passwordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.PASSWORD,su_password);
    }

    @FXML
    void userIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ccz.util.TextField.Uid,su_userID);
    }


    public boolean isValid() {
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.Eid,su_EmployeeId)) {
            return false;
        }
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.Uid,su_userID)) {
            return false;
        }
        if (!Regex.setTextColor(lk.ijse.ccz.util.TextField.PASSWORD,su_password)) {
            return false;
        }
        return true;
    }
}