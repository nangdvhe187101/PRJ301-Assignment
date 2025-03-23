/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllerRequest;

import dal.EmployeeDAO;
import dal.LeaveRequestDAO;
import data.Employee;
import data.LeaveRequests;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class EmployeeStatusController extends HttpServlet {
    private  static final SimpleDateFormat date  = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null || !"Manager".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee manager = employeeDAO.get(user.getEmployee().getEmployeeID());
        ArrayList<Employee> subordinates = (manager != null) ? manager.getDirectstaffs() : new ArrayList<>();
        request.setAttribute("subordinates", subordinates);
        request.setAttribute("activeTab", "status"); 
        request.getRequestDispatcher("/employee/Manager.jsp").forward(request, response);
    } 
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null ||  !"manager".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath()+"/Login");
            return;
        }
        EmployeeDAO eplDAO = new EmployeeDAO();
        Employee manager = eplDAO.get(user.getEmployee().getEmployeeID());
        ArrayList<Employee> subordinates = (manager != null) ? manager.getDirectstaffs() : new ArrayList<>();
        
        String employeeIDStr = request.getParameter("employeeID");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        if (employeeIDStr != null && startDateStr != null && endDateStr != null) {
            try {
                int employeeID = Integer.parseInt(employeeIDStr);
                Date startDate = date.parse(startDateStr);
                Date endDate = date.parse(endDateStr);
                
                //validate date
                if (startDate.after(endDate)) {
                    request.setAttribute("error", "Ngày bắt đầu không thể sau ngày kết thúc");
                }
                else{
                    LeaveRequestDAO lrDao = new LeaveRequestDAO();
                    ArrayList<LeaveRequests> approvedRequests = lrDao.getApprovedLeaveRequests(employeeID, startDateStr, endDateStr);
                    ArrayList<Boolean> statuses = new ArrayList<>();
                    ArrayList<String> dates = new ArrayList<>();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    while (!cal.getTime().after(endDate)) {                        
                        Date currentDate = cal.getTime();
                        dates.add(date.format(currentDate));
                        boolean isOnlive = false;
                        for (LeaveRequests lr : approvedRequests) {
                            if (!currentDate.before(lr.getFrom()) && !currentDate.after(lr.getTo())) {
                                isOnlive = true;
                                break;
                            }
                        }
                        statuses.add(!isOnlive);
                        cal.add(Calendar.DATE, 1);
                    }
                    request.setAttribute("statuses", statuses);
                    request.setAttribute("dates", dates);
                    
                    for (Employee emp : subordinates) {
                        if (emp.getEmployeeID() == employeeID) {
                            request.setAttribute("selectedEmployee", emp.getEmployeeName());
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Lỗi xử lý yêu cầu: " + e.getMessage());
            }
            
        }
        else{
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
        }
        request.setAttribute("activeTab", "status"); 
        request.getRequestDispatcher("/employee/Manager.jsp").forward(request, response);
    }
   
}
