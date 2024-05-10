package lk.ijse.ccz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ConfirmOrder {
    private Order order;
    private List<OrderDetail> odList;
}
