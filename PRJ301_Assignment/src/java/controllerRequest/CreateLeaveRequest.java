/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerRequest;

import controller.BaseRequiredUserController;
import dal.EmployeeDB;
import dal.LeaveRequestDB;
import data.Employee;
import data.LeaveRequests;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet("/employee/Create")
public class CreateLeaveRequest extends BaseRequiredUserController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        LeaveRequests lr = new LeaveRequests();
        lr.setTitle(req.getParameter("title"));
        lr.setFrom(Date.valueOf(req.getParameter("startDate")));
        lr.setTo(Date.valueOf(req.getParameter("endDate")));
        lr.setReason(req.getParameter("reason"));
        lr.setStatus(0);
        lr.setCreatedby(user);

        LeaveRequestDB lrdb = new LeaveRequestDB();
        try {
            lrdb.insert(lr);
            req.setAttribute("success", "Gửi đơn thành công! Mã đơn là: " + lr.getId());
        } catch (Exception ex) {
            req.setAttribute("error", "Gửi đơn thất bại! Lỗi: " + ex.getMessage());
        }

        // load lại trang JSP và hiển thị thông báo
        req.getRequestDispatcher("/employee/create.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        EmployeeDB db = new EmployeeDB();
        ArrayList<Employee> employees = db.list();
        req.setAttribute("employee", employees);
        req.getRequestDispatcher("../employee/create.jsp").forward(req, resp);
    }

}
