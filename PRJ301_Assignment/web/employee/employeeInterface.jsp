<%-- 
    Document   : employ
    Created on : Feb 22, 2025, 11:46:47 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
        <title>Trang chủ</title>
        <script>

            //
            let currentMonth = new Date().getMonth() + 1;//vì js tính từ 0-11 nên + 1 
            let currentYear = new Date().getFullYear();
            //thay đổi tháng năm
            function changeMonth(step)
            {
                currentMonth += step;
                if (currentMonth === 0)
                {
                    currentMonth = 12;
                    currentYear--;
                } else if (currentMonth === 13) {
                    currentMonth = 1;
                    currentYear++;
                }
                updateCalendar();
            }
            //tạo bảng lịch
            function generateCalendar()
            {
                const table = document.getElementById("calendar-body");
                table.innerHTML = "";
                let firstDay = new Date(currentYear, currentMonth - 1, 1).getDay();//xác định thứ mấy
                let daysMonth = new Date(currentYear, currentMonth, 0).getDate();//xác định 1 tháng bao nhiêu ngày
                let date = 1;
                for (let i = 0; i < 6; i++) {
                    let row = document.createElement("tr");//tạo hàng
                    for (let j = 0; j < 7; j++) {
                        let cell = document.createElement("td");//tạo ô
                        if (i === 0 && j < firstDay)
                        {
                            cell.innerHTML = "";//trống nếu chưa đến ngày đầu tiên trong tháng
                        } else if (date > daysMonth)
                        {
                            break;
                        } else
                        {
                            cell.innerHTML = date;//gán số vào ô
                            date++;//ngày ++
                        }
                        row.appendChild(cell);//thêm ô vào hàng
                    }
                    table.appendChild(row);//thêm hàng vào bảng
                }
            }

            //cập nhật ngày tháng và bảng lịch
            function updateCalendar()
            {
                document.getElementById("calendar-title").innerHTML = "Tháng " + currentMonth + "  năm" + currentYear;
                generateCalendar();
            }
            //chạy sau khi tải xong html
            document.addEventListener("DOMContentLoaded", updateCalendar);
        </script>
    </head>
    <body>
        <div class="navbar">
            <h2>Hệ Thống Quản Lý Nghỉ Phép</h2>
            <button class="logout">Đăng xuất</button>
        </div>
        <div class="main-container">
            <div class="sidebar">
                <h3>Chức vụ: ${sessionScope.userRole}</h3>
                <c:forEach var="feature" items="${sessionScope.userFeatures}">
                    <c:choose>
                        <c:when test="${feature == '/employee/employeeInterface.jsp'}">
                            <a href="${pageContext.request.contextPath}/employee/employeeInterface.jsp">🏠 Trang chủ</a>
                        </c:when>
                        <c:when test="${feature == '/employee/create.jsp'}">
                            <a href="${pageContext.request.contextPath}/employee/create.jsp">📝 Tạo đơn nghỉ phép</a>
                        </c:when>
                        <c:when test="${feature == '/employee/allOrders.jsp'}">
                            <a href="${pageContext.request.contextPath}/employee/allOrders.jsp">📄 Tất cả đơn đã tạo</a>
                        </c:when>
                        <c:when test="${feature == '/employee/leave-requests.jsp'}">
                            <a href="${pageContext.request.contextPath}/employee/leave-requests.jsp">🏠 Xem tất cả đơn nghỉ phép</a>
                        </c:when>
                        <c:when test="${feature == '/employee/employee-status.jsp'}">
                            <a href="${pageContext.request.contextPath}/employee/employee-status.jsp">📊 Xem tình trạng lao động</a>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </div>

            <div class="content">
                <h3>Chào mừng ${sessionScope.displayName}</h3>
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

            <!--creatOrder-->

        </div>
    </body>
</html>