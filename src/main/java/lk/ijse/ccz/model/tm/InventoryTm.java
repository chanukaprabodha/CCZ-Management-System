package lk.ijse.ccz.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class InventoryTm {
    private String id;
    private String name;
    private double stock;
    private double price;
}
