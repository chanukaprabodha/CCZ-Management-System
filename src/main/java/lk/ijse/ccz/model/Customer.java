package lk.ijse.ccz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Customer {
    private String customerID;
    private String name;
    private String email;
    private String address;
    private String contact;
}
