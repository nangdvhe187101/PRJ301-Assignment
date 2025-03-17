/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerRequest;

import dal.LeaveRequestDAO;
import data.LeaveRequests;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class UpdateLeaveRequest extends HttpServlet {

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
        processRequest(request, response);
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
        try {
            //lấy dữ liệu từ form
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String reason = request.getParameter("reason");
            Date form = Date.valueOf(request.getParameter("startDate"));
            Date to = Date.valueOf(request.getParameter("endDate"));


            //tạo đối tượng
            LeaveRequests model = new LeaveRequests();
            model.setId(id);
            model.setTitle(title);
            model.setReason(reason);
            model.setFrom(form);
            model.setTo(to);

            //gọi leaveRequetsDAO để update
            LeaveRequestDAO Dao = new LeaveRequestDAO();
            Dao.update(model);

            //thông báo thành công
            request.getSession().setAttribute("success", "Cập nhật đơn thành công");
            session.setAttribute("activeTab", "create-order");

        } catch (Exception e) {
            session.setAttribute("lỗi update đơn", e);
        }
        //chuyển hướng
        redirectToPreviousPage(request, response);
    }

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
                redirectPage = "/employee/Manager.jsp";
            }
            resp.sendRedirect(req.getContextPath() + redirectPage);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
