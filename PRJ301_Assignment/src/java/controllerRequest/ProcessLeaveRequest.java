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
import java.util.logging.Logger;

public class ProcessLeaveRequest extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProcessLeaveRequest.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String role = (String) session.getAttribute("userRole");
        if (role == null || (!"Director".equals(role) && !"Manager".equals(role))) {
            session.setAttribute("error", "Bạn không có quyền xử lý đơn nghỉ phép!");
            redirectToPreviousPage(request, response);
            return;
        }

        try {
            String idParam = request.getParameter("id");
            String action = request.getParameter("action");
            if (idParam == null || action == null) {
                session.setAttribute("error", "Dữ liệu không hợp lệ!");
                redirectToPreviousPage(request, response);
                return;
            }

            int id = Integer.parseInt(idParam);
            LeaveRequestDAO dao = new LeaveRequestDAO();
            LeaveRequests leaveRequest = dao.getLeaveRequestById(id);

            // Kiểm tra đơn có tồn tại không
            if (leaveRequest == null) {
                session.setAttribute("error", "Không tìm thấy đơn với ID: " + id);
                redirectToPreviousPage(request, response);
                return;
            }

            // Kiểm tra trạng thái đơn
            if (leaveRequest.getStatus() != 0) {
                session.setAttribute("error", "Đơn đã được xử lý, không thể thay đổi!");
                redirectToPreviousPage(request, response);
                return;
            }

            // Xác định trạng thái mới
            int newStatus = "approve".equalsIgnoreCase(action) ? 1 : 2; // 1: Approved, 2: Rejected
            dao.updateStatus(id, newStatus);

            session.setAttribute("success", "Đơn đã được " + ("approve".equalsIgnoreCase(action) ? "duyệt" : "từ chối") + " thành công!");
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid ID format: " + e.getMessage());
            session.setAttribute("error", "Dữ liệu không hợp lệ (ID): " + e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Error processing leave request: " + e.getMessage());
            session.setAttribute("error", "Lỗi khi xử lý đơn: " + e.getMessage());
        }

        // Chuyển hướng sau khi xử lý
        redirectToPreviousPage(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    private void redirectToPreviousPage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("userRole");
        String redirectPage = "/employee/Director";
        if ("Manager".equals(role)) {
            redirectPage = "/employee/Manager";
        }
        resp.sendRedirect(req.getContextPath() + redirectPage);
    }
}