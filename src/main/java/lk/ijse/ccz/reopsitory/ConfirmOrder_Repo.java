package lk.ijse.ccz.reopsitory;

import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.model.ConfirmOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class ConfirmOrder_Repo {
    public static boolean placeOrder(ConfirmOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = Order_Repo.save(po.getOrder());
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetail_Repo.save(po.getOdList());
                if (isOrderDetailSaved) {
                    boolean isItemQtyUpdate = Inventory_Repo.updateQty(po.getOdList());
                    if (isItemQtyUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
