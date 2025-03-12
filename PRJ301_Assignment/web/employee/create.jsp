<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
        <title>Tạo Đơn xin nghỉ</title>
        <script>
            // Hiển thị form khi trang được tải
            document.addEventListener("DOMContentLoaded", function () {
                const leaveForm = document.getElementById("leave-form");
                if (leaveForm) {
                    leaveForm.style.display = "block"; // Thay đổi display từ none thành block
                }
            });
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
                            <a href="${pageContext.request.contextPath}/employee/employee-statusjsp">📊 Xem tình trạng lao động</a>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </div>

            <!-- Leave Form Content -->
            <div class="leave-form" id="leave-form">                
                <h2>Đơn xin nghỉ phép</h2>
                <p>
                <p><b>User:</b>${sessionScope.displayName}</p><p><b>Department:</b> Sales</p><p><b>Role:</b>${sessionScope.userRole}</p>
                </p>
                <form action="${pageContext.request.contextPath}/employee/Create" method="POST">
                    <label>Tiêu đề:</label>
                    <input type="text" name="title" required/><br/>
                    <label>Từ ngày:</label>
                    <input type="date" name="startDate" required>
                    <label>Tới ngày:</label>
                    <input type="date" name="endDate" required>
                    <label>Lý do</label>
                    <textarea name="reason" required></textarea>
                    <div class="btn-group">
                        <button class="btn" type="submit" value="Send">Gửi</button>
                        <button class="btn" type="reset">Hủy</button>
                    </div>
                </form>
                <c:if test="${not empty success}">
                    <div style="color: green; font-weight: bold;">${success}</div>
                </c:if>
ss
                <c:if test="${not empty error}">
                    <div style="color: red; font-weight: bold;">${error}</div>
                </c:if>
            </div>
        </div>
    </body>
</html>