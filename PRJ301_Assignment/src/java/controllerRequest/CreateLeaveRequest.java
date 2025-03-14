/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerRequest;

import controller.BaseRequiredUserController;
import dal.LeaveRequestDAO;
import data.LeaveRequests;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class CreateLeaveRequest extends BaseRequiredUserController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        if (referer != null) {
            resp.sendRedirect(referer); 
        } else {
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            if ("Manager".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/employee/Manager.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/employee/Staff.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        try {
            // Log để kiểm tra user
            System.out.println("User creating leave request: " + user.getUsername());

            // Lấy thông tin từ form
            String title = req.getParameter("title");
            Date startDate = Date.valueOf(req.getParameter("startDate"));
            Date endDate = Date.valueOf(req.getParameter("endDate"));
            String reason = req.getParameter("reason");

            // Log để kiểm tra dữ liệu từ form
            System.out.println("Form data - Title: " + title + ", StartDate: " + startDate + ", EndDate: " + endDate + ", Reason: " + reason);

            // Tạo đối tượng gửi đơn
            LeaveRequests leave = new LeaveRequests();
            leave.setTitle(title);
            leave.setFrom(startDate);
            leave.setTo(endDate);
            leave.setReason(reason);
            leave.setStatus(0);
            leave.setCreatedby(user);

            // Lưu đơn vào cơ sở dữ liệu
            LeaveRequestDAO leaveDB = new LeaveRequestDAO();
            leaveDB.insert(leave);

            // Log sau khi insert
            System.out.println("Leave request inserted with ID: " + leave.getId());

            // Thông báo thành công
            req.setAttribute("success", "Đơn nghỉ phép đã được gửi thành công!");
            req.getRequestDispatcher("/employee/create.jsp").forward(req, resp);
        } catch (Exception e) {
            // Log lỗi chi tiết
            System.out.println("Error in CreateLeaveRequest: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi gửi đơn: " + e.getMessage());
            req.getRequestDispatcher("/employee/create.jsp").forward(req, resp);
        }
        String referer = req.getHeader("Referer");
        if (referer != null) {
            resp.sendRedirect(referer); 
        } else {
            HttpSession session = req.getSession();
            String role = (String) session.getAttribute("userRole");
            if ("Manager".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/employee/Manager.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/employee/Staff.jsp");
            }
        }
    }
}