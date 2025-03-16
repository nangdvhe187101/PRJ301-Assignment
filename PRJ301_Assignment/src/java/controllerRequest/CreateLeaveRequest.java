package controllerRequest;

import dal.LeaveRequestDAO;
import data.LeaveRequests;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.*;


/**
 *
 * @author ADMIN
 */
public class CreateLeaveRequest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Chuyển hướng về trang trước đó hoặc trang mặc định
        redirectToPreviousPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");

        // Kiểm tra đăng nhập
        if (user == null) {
            session.setAttribute("error", "Vui lòng đăng nhập!");
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        // Kiểm tra vai trò: chỉ Staff và Manager được tạo đơn
        String role = (String) session.getAttribute("userRole");
        if (!"Staff".equals(role) && !"Manager".equals(role)) {
            session.setAttribute("error", "Bạn không có quyền tạo đơn nghỉ phép!");
            redirectToPreviousPage(req, resp);
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String title = req.getParameter("title");
            Date startDate = Date.valueOf(req.getParameter("startDate"));
            Date endDate = Date.valueOf(req.getParameter("endDate"));
            String reason = req.getParameter("reason");

            // Tạo đối tượng LeaveRequests
            LeaveRequests leaveRequest = new LeaveRequests();
            leaveRequest.setTitle(title);
            leaveRequest.setFrom(startDate);
            leaveRequest.setTo(endDate);
            leaveRequest.setReason(reason);
            leaveRequest.setStatus(0); // Trạng thái mặc định: Đang xử lý
            leaveRequest.setCreatedby(user);

            // Lưu vào cơ sở dữ liệu
            LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
            leaveDAO.insert(leaveRequest);

            // Thông báo thành công
            session.setAttribute("success", "Đơn nghỉ phép đã được gửi thành công!");
        } catch (Exception e) {
            // Thông báo lỗi
            session.setAttribute("error", "Lỗi khi gửi đơn: " + e.getMessage());
        }

        // Chuyển hướng về trang trước đó
        redirectToPreviousPage(req, resp);
    }

    // Phương thức hỗ trợ chuyển hướng về trang trước đó
    private void redirectToPreviousPage(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        HttpSession session = req.getSession();
        String referer = req.getHeader("Referer");
        String role = (String) session.getAttribute("userRole");

        if (referer != null && !referer.contains("Login")) {
            resp.sendRedirect(referer);
        } else {
            String redirectPage = "/employee/Staff.jsp"; 
            if ("Manager".equals(role)) {
                redirectPage = "/employee/Manager.jsp";
            }
            resp.sendRedirect(req.getContextPath() + redirectPage);
        }
    }
}