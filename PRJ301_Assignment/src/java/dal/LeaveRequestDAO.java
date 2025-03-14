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
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Câu lệnh SQL (sửa LeaveRequests thành LeaveRequest)
            String sql = "INSERT INTO dbo.LeaveRequest (title, reason, [from], [to], status, createdby, createddate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Gán giá trị cho các tham số
            stmt.setString(1, model.getTitle());
            stmt.setString(2, model.getReason());
            stmt.setDate(3, model.getFrom());
            stmt.setDate(4, model.getTo());
            stmt.setInt(5, model.getStatus());
            stmt.setString(6, model.getCreatedby().getUsername());

            // Thực thi câu lệnh
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Không thể chèn đơn nghỉ phép.");
            }

            // Lấy ID được sinh tự động
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
                System.out.println("Đơn nghỉ phép được tạo với ID: " + model.getId());
            } else {
                throw new SQLException("Không thể lấy ID tự động sinh ra.");
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
                System.out.println("Rollback do lỗi: " + ex.getMessage());
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("Lỗi khi tạo đơn nghỉ phép: " + ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
