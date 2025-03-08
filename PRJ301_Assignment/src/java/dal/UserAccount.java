/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class UserAccount extends DBContext {

    public User get(String username, String password) {
        String sql = "SELECT username,displayname FROM [User]\n"
                + "WHERE username = ? AND [password] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(username);
                user.setDisplayname(rs.getString("displayname"));
                return user;
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
        return null;
    }
}
