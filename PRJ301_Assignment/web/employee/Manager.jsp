<%-- 
    Document   : Manager
    Created on : Mar 14, 2025, 10:00:02 AM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session == null || session.getAttribute("account") == null) {
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
        return;
    }
    String userRole = (String) session.getAttribute("userRole");
    if (!"Manager".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
        <title>Hệ Thống Quản Lý Nghỉ Phép - Quản Lý</title>
        <script>
            let currentMonth = new Date().getMonth() + 1;
            let currentYear = new Date().getFullYear();

            function showSection(sectionId) {
                let sections = ["calendar", "leave-request", "status", "all-orders", "create-order"];
                sections.forEach(id => {
                    document.getElementById(id).style.display = "none";
                });
                document.getElementById(sectionId).style.display = "block";
            }

            function changeMonth(step) {
                currentMonth += step;
                if (currentMonth === 0) {
                    currentMonth = 12;
                    currentYear--;
                } else if (currentMonth === 13) {
                    currentMonth = 1;
                    currentYear++;
                }
                updateCalendar();
            }

            function generateCalendar() {
                const table = document.getElementById("calendar-body");
                table.innerHTML = "";
                let firstDay = new Date(currentYear, currentMonth - 1, 1).getDay();
                let daysMonth = new Date(currentYear, currentMonth, 0).getDate();
                let date = 1;
                for (let i = 0; i < 6; i++) {
                    let row = document.createElement("tr");
                    for (let j = 0; j < 7; j++) {
                        let cell = document.createElement("td");
                        if (i === 0 && j < firstDay) {
                            cell.innerHTML = "";
                        } else if (date > daysMonth) {
                            break;
                        } else {
                            cell.innerHTML = date;
                            date++;
                        }
                        row.appendChild(cell);
                    }
                    table.appendChild(row);
                }
            }

            function updateCalendar() {
                document.getElementById("calendar-title").innerHTML = "Tháng " + currentMonth + "  năm " + currentYear;
                generateCalendar();
            }

            document.addEventListener("DOMContentLoaded", function () {
                showSection('calendar');
                updateCalendar();
            });
        </script>
    </head>
    <body>
        <div class="navbar">
            <h2>Hệ Thống Quản Lý Nghỉ Phép - Quản Lý</h2>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a>
        </div>
        <div class="main-container">
            <div class="sidebar">
                <h3>Chức vụ: Manager</h3>
                <a onclick="showSection('calendar')">🏠 Trang chủ</a>
                <a onclick="showSection('create-order')">✍️ Tạo đơn nghỉ phép</a>
                <a onclick="showSection('all-orders')">📦 Xem tất cả đơn đã tạo</a>
                <a onclick="showSection('leave-request')">📄 Xem tất cả đơn nghỉ phép</a>
                <a onclick="showSection('status')">📊 Xem tình trạng lao động</a>
  
            </div>

            <div id="calendar" class="content">
                <h3>Chào mừng</h3>
                <div class="calendar">
                    <div class="calendar-header">
                        <button onclick="changeMonth(-1)">◀ Tháng trước</button>
                        <h4 id="calendar-title"></h4>
                        <button onclick="changeMonth(+1)">Tháng Sau ▶</button>
                    </div>
                    <table class="calendar-table">
                        <thead>
                            <tr>
                                <th>Chủ Nhật</th>
                                <th>Thứ hai</th>
                                <th>Thứ ba</th>
                                <th>Thứ tư</th>
                                <th>Thứ năm</th>
                                <th>Thứ sáu</th>
                                <th>Thứ bảy</th>
                            </tr>
                        </thead>
                        <tbody id="calendar-body"></tbody>
                    </table>
                </div>
            </div>

            <div id="leave-request" class="content" style="display: none;">
                <h1>Quản Lý Đơn Nghỉ Phép</h1>
                <table class="leave-request-table">
                    <thead>
                        <tr>
                            <th>Tiêu đề</th>
                            <th>Từ ngày</th>
                            <th>Tới ngày</th>
                            <th>Nhân viên tạo</th>
                            <th>Tình trạng</th>
                            <th>Quản lý</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Xin nghỉ cưới</td>
                            <td>1/1/2025</td>
                            <td>3/1/2025</td>
                            <td>Mr F</td>
                            <td>Đang xử lý</td>
                            <td>
                                <button class="status-btn approve-btn">✅ Duyệt</button>
                                <button class="status-btn reject-btn">❌ Từ chối</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Xin nghỉ đi chơi</td>
                            <td>1/1/2025</td>
                            <td>5/1/2025</td>
                            <td>Mr E</td>
                            <td>Đã từ chối</td>
                            <td>
                                <button class="status-btn approve-btn" disabled>✅ Duyệt</button>
                                <button class="status-btn reject-btn" disabled>❌ Từ chối</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div id="status" class="content" style="display: none;">
                <h1>Tình Trạng Lao Động</h1>
                <table class="employee-status-table">
                    <thead>
                        <tr>
                            <th>Nhân viên</th>
                            <th>1/1</th>
                            <th>2/1</th>
                            <th>3/1</th>
                            <th>4/1</th>
                            <th>5/1</th>
                            <th>6/1</th>
                            <th>7/1</th>
                            <th>8/1</th>
                            <th>9/1</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Mr A</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-red">❌</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                        </tr>
                        <tr>
                            <td>Mr B</td>
                            <td class="status-red">❌</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-red">❌</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                            <td class="status-green">✅</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div id="all-orders" class="content" style="display: none;">
                <h2>Tất Cả Đơn Đã Tạo</h2>
                <table class="leave-request-table">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Created By</th>
                            <th>Status</th>
                            <th>Processed By</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>
                                <a href="update.jsp" class="status-btn approve-btn">Update</a>
                                <a href="delete.jsp" class="status-btn reject-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa đơn này?')">Delete</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7">Bạn chưa tạo đơn nghỉ phép nào.</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div id="create-order" class="content" style="display: none;">
                <h2>Tạo Đơn Xin Nghỉ Phép</h2>
                <p><b>User:</b> ${sessionScope.displayName}</p>
                <p><b>Department:</b> Sales</p>
                <p><b>Role:</b> ${sessionScope.userRole}</p>
                <form action="${pageContext.request.contextPath}/employee/create" method="POST">
                    <label>Tiêu đề:</label>
                    <input type="text" name="title" required/><br/>
                    <label>Từ ngày:</label>
                    <input type="date" name="startDate" required><br/>
                    <label>Tới ngày:</label>
                    <input type="date" name="endDate" required><br/>
                    <label>Lý do:</label>
                    <textarea name="reason" required></textarea><br/>
                    <div class="btn-group">
                        <button class="btn" type="submit">Gửi</button>
                        <button class="btn" type="reset">Hủy</button>
                    </div>
                </form>
                <c:if test="${not empty success}">
                    <div style="color: green; font-weight: bold;">${success}</div>
                    <% session.removeAttribute("success"); %>
                </c:if>
                <c:if test="${not empty error}">
                    <div style="color: red; font-weight: bold;">${error}</div>
                    <% session.removeAttribute("error"); %>
                </c:if>
            </div>
        </div>
    </body>
</html>
