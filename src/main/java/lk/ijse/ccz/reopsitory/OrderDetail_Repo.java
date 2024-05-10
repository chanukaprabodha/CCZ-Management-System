package lk.ijse.ccz.reopsitory;

import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetail_Repo {

        public static boolean save(List<OrderDetail> odList) throws SQLException {
            for (OrderDetail od : odList) {
                if(!save(od)) {
                    return false;
                }
            }
            return true;
    }

    private static boolean save(OrderDetail od) throws SQLException {
        String sql = "INSERT INTO orderdetail VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getIngredientId());
        pstm.setInt(3, (int) od.getIngredientQty());
        pstm.setDouble(4, od.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }
}
