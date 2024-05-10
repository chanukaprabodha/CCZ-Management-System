package lk.ijse.ccz.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeTm {

    private String employeeId;
    private String name;
    private String position;
    private String address;
    private String contact;
}
