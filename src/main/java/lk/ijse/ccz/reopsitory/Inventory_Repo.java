package lk.ijse.ccz.reopsitory;

import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.model.Inventory;
import lk.ijse.ccz.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventory_Repo {


    public static boolean save(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO ingredient VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

            pstm.setObject(1, inventory.getId());
            pstm.setObject(2, inventory.getName());
            pstm.setObject(3, inventory.getStock());
            pstm.setObject(4, inventory.getPrice());

            return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM ingredient WHERE ing_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

            pstm.setObject(1, id);

            return pstm.executeUpdate() > 0;

    }

    public static boolean update(Inventory inventory) throws SQLException {
        String sql = "UPDATE ingredient SET ing_name = ?, stock = ?, price = ? WHERE ing_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

            pstm.setObject(1, inventory.getName());
            pstm.setObject(2, inventory.getStock());
            pstm.setObject(3, inventory.getPrice());
            pstm.setObject(4, inventory.getId());

            return pstm.executeUpdate() > 0;

    }

    public static List<Inventory> getAll() throws SQLException {
        String sql = "SELECT * FROM ingredient";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Inventory> inventoryList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            double stock = Double.parseDouble(resultSet.getString(3));
            double price = Double.parseDouble(resultSet.getString(4));

            Inventory inventory = new Inventory(id, name, stock, price);
            inventoryList.add(inventory);
        }
        return inventoryList;

    }

    public static boolean updateQty(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetail od) throws SQLException {
        String sql = "UPDATE ingredient SET stock = stock - ? WHERE ing_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

            pstm.setDouble(1, od.getIngredientQty());
            pstm.setString(2, od.getIngredientId());

            return pstm.executeUpdate() > 0;

    }

    public static double getUnitPrice(String recipe) throws SQLException {
        String sql = "SELECT price FROM ingredient WHERE ing_id = ?";


        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, recipe);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public static String getProductId(String cellData) throws SQLException {
        String sql = "SELECT ing_id FROM ingredient WHERE ing_name = ?";

        System.out.println(cellData);

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, cellData);
        ResultSet resultSet = pstm.executeQuery();


        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
