/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.LeaveRequests;
import data.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class LeaveRequestDAO extends DBContext<LeaveRequests> {

    public void insert(LeaveRequests model) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [LeaveRequests] ([title], [reason], [from], [to], [status], [createdby], [createddate]) "
                    + "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, model.getTitle());
            stm.setString(2, model.getReason());
            stm.setDate(3, model.getFrom());
            stm.setDate(4, model.getTo());
            stm.setInt(5, model.getStatus());
            stm.setString(6, model.getCreatedby().getUsername());

            // Log trước khi thực thi
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + model.getTitle() + ", " + model.getReason() + ", "
                    + model.getFrom() + ", " + model.getTo() + ", " + model.getStatus() + ", "
                    + model.getCreatedby().getUsername());

            int rowsAffected = stm.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("SQL Error in insert: " + ex.getMessage());
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            throw new RuntimeException("Error inserting leave request: " + ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
