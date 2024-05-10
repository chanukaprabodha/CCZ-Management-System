package lk.ijse.ccz.reopsitory;

import lk.ijse.ccz.db.DbConnection;
import lk.ijse.ccz.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee_Repo {

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?,?)";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql)) {

            pstm.setObject(1, employee.getEmployeeID());
            pstm.setObject(2, employee.getName());
            pstm.setObject(3, employee.getPosition());
            pstm.setObject(4, employee.getAddress());
            pstm.setObject(5, employee.getContact());

            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employeeId = ?";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql)) {

            pstm.setObject(1, id);

            return pstm.executeUpdate() > 0;
        }

    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?,position = ?, address = ?, contact = ? WHERE employeeId = ?";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql)) {

            pstm.setObject(1, employee.getName());
            pstm.setObject(2, employee.getPosition());
            pstm.setObject(3, employee.getAddress());
            pstm.setObject(4, employee.getContact());
            pstm.setObject(5, employee.getEmployeeID());

            return pstm.executeUpdate() > 0;
        }
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String position = resultSet.getString(3);
            String address = resultSet.getString(4);
            String contact = resultSet.getString(5);

            Employee employee = new Employee(id, name, position, address, contact);
            employeeList.add(employee);
        }
        return employeeList;
    }
}
