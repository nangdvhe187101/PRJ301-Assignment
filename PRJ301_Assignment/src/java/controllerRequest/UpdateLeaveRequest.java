/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import java.io.PrintWriter;
import java.sql.Date;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */

public class UpdateLeaveRequest extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateLeaveRequest.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateLeaveRequest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateLeaveRequest at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/employee/Staff").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("error", "Vui lòng đăng nhập!");
            request.getRequestDispatcher("/employee/Staff").forward(request, response);
            return;
        }

        String role = (String) session.getAttribute("userRole");
        if (!"Staff".equals(role) && !"Manager".equals(role)) {
            session.setAttribute("error", "Bạn không có quyền cập nhật đơn nghỉ phép!");
            request.getRequestDispatcher("/employee/Staff").forward(request, response);
            return;
        }

        try {

            String idParam = request.getParameter("id");
            String title = request.getParameter("title");
            String reason = request.getParameter("reason");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            LOGGER.info("Received parameters - id: " + idParam + ", title: " + title +
                       ", startDate: " + startDateStr + ", endDate: " + endDateStr + ", reason: " + reason);

            int id = Integer.parseInt(idParam);
            Date from = Date.valueOf(startDateStr);
            Date to = Date.valueOf(endDateStr);

            // Kiểm tra quyền và trạng thái đơn
            LeaveRequestDAO dao = new LeaveRequestDAO();
            LeaveRequests existingRequest = dao.getLeaveRequestById(id); 
            if (existingRequest == null) {
                session.setAttribute("error", "Không tìm thấy đơn với ID: " + id);
                session.setAttribute("activeTab", "all-orders");
                redirectToPreviousPage(request, response);
                return;
            }
            if (!existingRequest.getCreatedby().getUsername().equals(user.getUsername())) {
                session.setAttribute("error", "Bạn không có quyền cập nhật đơn này!");
                session.setAttribute("activeTab", "all-orders");
                redirectToPreviousPage(request, response);
                return;
            }
            if (existingRequest.getStatus() != 0) {
                session.setAttribute("error", "Đơn đã được xử lý, không thể cập nhật!");
                session.setAttribute("activeTab", "all-orders");
                redirectToPreviousPage(request, response);
                return;
            }

            // Cập nhật đối tượng
            LeaveRequests model = new LeaveRequests();
            model.setId(id);
            model.setTitle(title);
            model.setReason(reason);
            model.setFrom(from);
            model.setTo(to);

            dao.update(model);
            session.setAttribute("success", "Cập nhật đơn thành công");
            session.setAttribute("activeTab", "all-orders");
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid ID format: " + e.getMessage());
            session.setAttribute("error", "Dữ liệu không hợp lệ (ID): " + e.getMessage());
            session.setAttribute("activeTab", "all-orders");
        } catch (IllegalArgumentException e) {
            LOGGER.severe("Invalid date format: " + e.getMessage());
            session.setAttribute("error", "Ngày không hợp lệ: " + e.getMessage());
            session.setAttribute("activeTab", "all-orders");
        } catch (Exception e) {
            LOGGER.severe("Error updating leave request: " + e.getMessage());
            session.setAttribute("error", "Lỗi khi cập nhật đơn: " + e.getMessage());
            session.setAttribute("activeTab", "all-orders");
        }
  
        redirectToPreviousPage(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private void redirectToPreviousPage(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        HttpSession session = req.getSession();
        String referer = req.getHeader("Referer");
        String role = (String) session.getAttribute("userRole");

        if (referer != null && !referer.contains("Login")) {
            resp.sendRedirect(referer);
        } else {
            String redirectPage = "/employee/Staff"; 
            if ("Manager".equals(role)) {
                redirectPage = "/employee/Manager";
            }
            resp.sendRedirect(req.getContextPath() + redirectPage);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
