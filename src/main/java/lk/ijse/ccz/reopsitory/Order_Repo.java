package lk.ijse.ccz.reopsitory;

import javafx.scene.chart.XYChart;
import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.model.Order;

import java.sql.*;

public class Order_Repo {
    public static String currentId() throws SQLException {
            String sql = "SELECT orderId FROM orders ORDER BY CAST(SUBSTRING(orderId, 2) AS UNSIGNED) DESC LIMIT 1";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
    }

    public static int getUnitPrice(String recipe) throws SQLException {
        String sql = "SELECT price FROM ingredient WHERE ing_name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, recipe);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setDate(2, (Date) order.getOrderDate());
        pstm.setString(3, order.getCustomerID());
        pstm.setDouble(4, order.getTotalAmount());

        return pstm.executeUpdate() > 0;
    }

    public static XYChart.Series incomeChart(XYChart.Series chart) {

        String sql = "SELECT orderDate, SUM(totalAmount) FROM orders GROUP BY orderDate ORDER BY TIMESTAMP(orderDate)";

        try {
            ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                chart.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getFloat(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chart;
    }

    public static XYChart.Series customerChart(XYChart.Series chart) {

        String sql = "SELECT orderDate, COUNT(orderId) FROM orders GROUP BY orderDate ORDER BY TIMESTAMP(orderDate)";

        try {
            ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                chart.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chart;
    }

    public static double getTodayIncome(Date sqlDate) throws SQLException {

        String sql = "SELECT SUM(totalAmount) FROM orders WHERE orderDate = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setDate(1, sqlDate);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public static double getTotalIncome() throws SQLException {

        String sql = "SELECT SUM(totalAmount) FROM orders";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        if(resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public static int getTodayOrder(Date sqlDate) throws SQLException {

        String sql = "SELECT COUNT(orderId) FROM orders WHERE orderDate = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setDate(1, sqlDate);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static String getMostRecentOrderId() throws SQLException {
        String sql = "select orderId from orders order by orderId desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null ;
    }
}
