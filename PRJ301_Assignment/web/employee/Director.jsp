<%-- 
    Document   : Director
    Created on : Mar 14, 2025, 10:00:02 AM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session == null || session.getAttribute("account") == null) {
        response.sendRedirect(request.getContextPath() + "/Login");
        return;
    }
    String userRole = (String) session.getAttribute("userRole");
    if (!"Director".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/Login");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
        <title>Trang chủ</title>
        <script>
            let currentMonth = new Date().getMonth() + 1;
            let currentYear = new Date().getFullYear();

            function showSection(sectionId) {
                let sections = ["calendar", "leave-request", "status"];
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
                showSection('calendar'); // Hiển thị trang chủ khi load
                updateCalendar();
            });
        </script>
    </head>
    <body>
        <div class="navbar">
            <h2>Hệ Thống Quản Lý Nghỉ Phép</h2>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a>
        </div>
        <div class="main-container">
            <div class="sidebar">
                <h4>Chức vụ: Director</h4>
                <a onclick="showSection('calendar')">🏠 Trang chủ</a>
                <a onclick="showSection('leave-request')">📄 Đơn của nhân viên</a>
                <a onclick="showSection('status')">📊 Tình trạng lao động</a>
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
                            <td>Title</td>
                            <td>From</td>
                            <td>To</td>
                            <td>Created By</td>
                            <td>Status</td>
                            <td>Actions</td>
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
        </div>
    </body>
</html>