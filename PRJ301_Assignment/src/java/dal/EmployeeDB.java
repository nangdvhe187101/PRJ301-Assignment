/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Department;
import data.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class EmployeeDB extends DBContext {

    public Employee get(int id) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "WITH employee_hierarchy AS (\n"
                    + "    SELECT employeeID, managerID, 0 AS level\n"
                    + "    FROM Employee\n"
                    + "    WHERE employeeID = ?\n"
                    + "    UNION ALL\n"
                    + "    SELECT e.employeeID, e.managerID, eh.level + 1\n"
                    + "    FROM Employee e\n"
                    + "    INNER JOIN employee_hierarchy eh ON e.managerID = eh.employeeID\n"
                    + ")\n"
                    + "SELECT \n"
                    + "    e.employeeID AS [staffID], \n"
                    + "    staff.employeeName AS [staffName],\n"
                    + "    e.managerID AS [managerID],\n"
                    + "    manager.employeeName AS [managerName],\n"
                    + "    d.departmentID AS [deptID],\n"
                    + "    d.departmentName AS [deptName]\n"
                    + "FROM employee_hierarchy e \n"
                    + "INNER JOIN Employee staff ON staff.employeeID = e.employeeID\n"
                    + "INNER JOIN Department d ON d.departmentID = staff.departmentID\n"
                    + "LEFT JOIN Employee manager ON e.managerID = manager.employeeID";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeID(rs.getInt("staffid"));
                e.setEmployeeName(rs.getString("staffname"));
                int managerID = rs.getInt("managerID");
                if (managerID != 0) {
                    Employee m = new Employee();
                    m.setEmployeeID(rs.getInt("managerID"));
                    m.setEmployeeName(rs.getString("managerName"));
                    m.setManager(m);
                }
                Department d = new Department();
                d.setDepartmentID(rs.getInt("departmentID"));
                d.setDepartmentName(rs.getString("departmentName"));
                e.setDpt(d);
                employees.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeDB.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
        //xây dựng cấu trúc phân cấp
        if (employees.size() > 0) {
            Employee root = employees.get(0);
            for (Employee employee : employees) {
                if (root!=employee) {
                    Employee manager = findManager(employees, employee);
                    employee.setManager(manager);
                    manager.getDirectstaffs().add(employee);
                    root.getStaffs().add(employee);
                }
            }
            return root;
        } else {
            return null;
        }
        
    }
    
    private Employee findManager(ArrayList<Employee> emps, Employee e){
        for (Employee emp : emps) {
            if (e.getManager().getEmployeeID() == emp.getEmployeeID()) {
                return emp;
            }
        }
        return null;
    }
}
