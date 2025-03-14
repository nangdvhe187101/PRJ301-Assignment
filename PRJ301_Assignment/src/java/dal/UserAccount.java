/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Employee;
import data.Features;
import data.Roles;
import data.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class UserAccount extends DBContext<User> {

    public User get(String username, String password) {
        User user = null;
        List<String> features = new ArrayList<>();
        String sql = "SELECT DISTINCT u.username, u.displayname, u.employeeID, r.roleName, f.url\n"
                + "FROM [User] u\n"
                + "LEFT JOIN User_role ur ON u.username = ur.username\n"
                + "LEFT JOIN Roles r ON ur.roleID = r.roleID\n"
                + "LEFT JOIN Role_features rf ON rf.roleID = r.roleID\n"
                + "LEFT JOIN Features f ON rf.featuresID = f.featuresID\n"
                + "WHERE u.username = ? AND u.password = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setUsername(username);
                    user.setDisplayname(rs.getString("displayname"));
                    user.setRoleName(rs.getString("roleName"));

                    // Thiết lập employee
                    Employee employee = new Employee();
                    employee.setEmployeeID(rs.getInt("employeeID"));
                    user.setEmployee(employee);

                    // Tải danh sách roles
                    RoleDAO roleDB = new RoleDAO();
                    ArrayList<Roles> roles = roleDB.getRolebyUsername(username);
                    for (Roles role : roles) {
                        ArrayList<Features> roleFeatures = roleDB.getFeatures(role.getRoleID());
                        role.setFeatures(roleFeatures);
                    }
                    user.setRoles(roles);
                }
                String url = rs.getString("url");
                if (url != null && !features.contains(url)) {
                    features.add(url);
                }
            }
            if (user != null) {
                user.setFeatures(features);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserAccount.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    Logger.getLogger(UserAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return user;
    }
}
