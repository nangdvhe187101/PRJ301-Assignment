/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Features;
import data.Roles;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class RoleDB extends DBContext<Roles> {

    public ArrayList<Roles> getRolebyUsername(String username) {
        ArrayList<Roles> roles = new ArrayList<>();
        try {
            String sql = "SELECT r.roleID, r.roleName "
                    + "FROM Roles r "
                    + "INNER JOIN User_role ur ON r.roleID = ur.roleID "
                    + "WHERE ur.username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Roles role = new Roles();
                role.setRoleID(rs.getInt("roleID"));
                role.setRoleName(rs.getString("roleName"));             
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }
    
    //Lấy danh sách features theo roleID từ bảng Role_Features và Features.
    public ArrayList<Features> getFeatures(int roleID) {
        ArrayList<Features> featureses = new ArrayList<>();
        try {
            String sql = "SELECT f.featuresID, f.url " +
                        "FROM Features f " +
                        "INNER JOIN Role_Features rf ON f.featuresID = rf.featuresID " +
                        "WHERE rf.roleID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, roleID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Features features = new Features();
                features.setFeaturesID(rs.getInt("featuresID"));
                features.setUrl(rs.getString("url"));             
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return featureses;
    }
}
