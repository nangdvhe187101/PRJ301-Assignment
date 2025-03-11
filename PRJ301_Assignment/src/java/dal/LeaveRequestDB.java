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
public class LeaveRequestDB extends DBContext {

    public void insert(LeaveRequests model) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [LeaveRequests]\n"
                    + "           ([title]\n"
                    + "           ,[reason]\n"
                    + "           ,[from]\n"
                    + "           ,[to]\n"
                    + "           ,[status]\n"
                    + "           ,[createdby]\n"
                    + "           ,[owner_employeeID]\n"
                    + "           ,[createddate])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,0\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getTitle());
            stm.setDate(2, model.getFrom());
            stm.setDate(3, model.getTo());
            stm.setString(4,model.getReason());
            stm.setString(5, model.getCreatedby().getUsername());
            stm.setInt(6, model.getOwner().getEmployeeID());
            stm.executeUpdate();
            //lấy ID của hồ sơ
            String sql_getID = "SELECT @@IDENTITY as roleID;";
            PreparedStatement stm_getid = connection.prepareStatement(sql_getID);
            ResultSet rs = stm_getid.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("roleID"));
            }
            connection.commit();
        } catch (SQLException ex) {
             Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
             try {
                connection.rollback();
            } catch (SQLException ex1) {
                 Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
}
