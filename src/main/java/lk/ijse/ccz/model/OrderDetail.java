package lk.ijse.ccz.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetail {
    private String orderId;
    private String ingredientId;
    private double ingredientQty;
    private double unitPrice;
}
