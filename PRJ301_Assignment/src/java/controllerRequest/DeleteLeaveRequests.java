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
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class DeleteLeaveRequests extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteLeaveRequests.class.getName());

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
            out.println("<title>Servlet DeleteLeaveRequests</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteLeaveRequests at " + request.getContextPath() + "</h1>");
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
        //lấy session user
        redirectToPreviousPage(request, response);
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
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        //kiểm tra vai trò 
        String role = (String) session.getAttribute("userRole");
        if (!"Staff".equals(role) && !"Manager".equals(role)) {
            session.setAttribute("error-all", "Bạn không có quyền cập nhật đơn nghỉ phép!");
            request.getRequestDispatcher("/employee/Staff").forward(request, response);
            return;
        }
        try {
            //Lấy id của đơn từ request
            String idlr = request.getParameter("id");
            if (idlr == null || idlr.isEmpty()) {
                session.setAttribute("error-all", "Đơn không hợp lệ");
                redirectToPreviousPage(request, response);
                return;
            }
            int id = Integer.parseInt(idlr);
            LeaveRequestDAO dao = new LeaveRequestDAO();
            LeaveRequests lr = dao.getLeaveRequestById(id);

            //kiểm tra đơn có tồn tại ko
            if (lr == null) {
                session.setAttribute("error-all", "Không tìm thấy đơn với ID: " + id);
                redirectToPreviousPage(request, response);
                return;
            }
            //kiểm tra quyền xóa 
            if (!lr.getCreatedby().getUsername().equals(user.getUsername())) {
                session.setAttribute("error-all", "Bạn không có quyền xóa đơn này");
                redirectToPreviousPage(request, response);
                return;
            }
            //kiểm tra trạng thái đơn
            if (lr.getStatus() != 0) {
                session.setAttribute("error-all", "Đơn đã được xử lý");
                redirectToPreviousPage(request, response);
                return;
            }
            //xóa đơn 
            dao.delete(id);
            session.setAttribute("success-all", "Xóa đơn thành công");
            session.setAttribute("activeTab", "all-orders");
        } catch (NumberFormatException ex) {
            LOGGER.severe("Invalid ID format: " + ex.getMessage());
            session.setAttribute("error-all", "Dữ liệu không hợp lệ (ID): " + ex.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Error deleting leave request: " + e.getMessage());
            session.setAttribute("error-all", "Lỗi khi xóa đơn: " + e.getMessage());
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
        String role = (String) session.getAttribute("userRole");
        String redirectPage = "/employee/Staff";
        if ("Manager".equals(role)) {
            redirectPage = "/employee/Manager";
        }
        resp.sendRedirect(req.getContextPath() + redirectPage);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
