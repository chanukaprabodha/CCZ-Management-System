package lk.ijse.ccz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
    private String orderId;
    private Date orderDate;
    private String customerID;
    private double totalAmount;
}
