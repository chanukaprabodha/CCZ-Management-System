package lk.ijse.ccz.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderTm {
    private String Recipes;
    private double Quantity;
    private double Price;
}
