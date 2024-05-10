package lk.ijse.ccz.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Employee {

    private String employeeID;
    private String name;
    private String position;
    private String address;
    private String contact;

}
