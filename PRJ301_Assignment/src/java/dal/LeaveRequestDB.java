/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Employee;
import data.LeaveRequests;
import data.User;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class LeaveRequestDB extends DBContext<LeaveRequests> {

    public void insert(LeaveRequests model) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [LeaveRequests] ([title],[reason],[from],[to],[status],[createdby],[createddate]) "
                    + "VALUES (?,?,?,?,?,?,GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, model.getTitle());
            stm.setString(2, model.getReason());
            stm.setDate(3, model.getFrom());
            stm.setDate(4, model.getTo());
            stm.setInt(5, model.getStatus()); 
            stm.setString(6, model.getCreatedby().getUsername());

            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
