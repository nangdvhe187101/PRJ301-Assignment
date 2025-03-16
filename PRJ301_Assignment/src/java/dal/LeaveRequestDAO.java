/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.LeaveRequests;
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

    public List<LeaveRequests> getLeaveRequests(String username) {
        List<LeaveRequests> leaveRequests = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT \n"
                    + "    lr.leaveRequestID,\n"
                    + "    lr.title,\n"
                    + "    lr.reason,\n"
                    + "    lr.[from],\n"
                    + "    lr.[to],\n"
                    + "    lr.status,\n"
                    + "    lr.createdby,\n"
                    + "    lr.createddate,\n"
                    + "    manager.displayname AS managerDisplayName\n" // Đặt alias rõ ràng
                    + "FROM \n"
                    + "    dbo.LeaveRequest lr\n"
                    + "LEFT JOIN \n"
                    + "    [User] u ON lr.createdby = u.username\n"
                    + "LEFT JOIN \n"
                    + "    Employee e ON u.employeeID = e.employeeID\n"
                    + "LEFT JOIN \n"
                    + "    Employee managerEmp ON e.managerID = managerEmp.employeeID\n"
                    + "LEFT JOIN \n"
                    + "    [User] manager ON managerEmp.employeeID = manager.employeeID\n"
                    + "WHERE \n"
                    + "    lr.createdby = ?;";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();

            while (rs.next()) {
                LeaveRequests leaveRequest = new LeaveRequests();
                leaveRequest.setId(rs.getInt("leaveRequestID"));
                leaveRequest.setTitle(rs.getString("title"));
                leaveRequest.setReason(rs.getString("reason"));
                leaveRequest.setFrom(rs.getDate("from"));
                leaveRequest.setTo(rs.getDate("to"));
                leaveRequest.setStatus(rs.getInt("status"));
                User createdby = new User();
                createdby.setUsername(rs.getString("createdby"));
                leaveRequest.setCreatedby(createdby);
                leaveRequest.setCreateddate(rs.getTimestamp("createddate"));

                //tham chiếu tên người quản lý trực tiếp
                String managerDisplayName = rs.getString("managerDisplayName");
                leaveRequest.setProcessedByDisplayName(managerDisplayName != null ? managerDisplayName : "Chưa xử lý");
                leaveRequests.add(leaveRequest);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách đơn nghỉ phép", ex);
            throw new RuntimeException("Lỗi khi lấy danh sách đơn nghỉ phép: " + ex.getMessage(), ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDAO.class.getName()).log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
                ex.printStackTrace();
            }
        }
        System.out.println("Fetched " + leaveRequests.size() + " leave requests for username: " + username);
        return leaveRequests;
    }
}
