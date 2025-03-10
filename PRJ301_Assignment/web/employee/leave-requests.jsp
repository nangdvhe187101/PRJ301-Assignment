<%-- 
    Document   : leave-requests
    Created on : Mar 5, 2025, 1:29:21 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Đơn Nghỉ Phép</title>
        <link rel="stylesheet" href="../styles.css">
    </head>
    <body>
        <div class="navbar">
            <h2>Hệ Thống Quản Lý Nghỉ Phép - Quản Lý</h2>
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
                <h3>Quản lý đơn nghỉ phép</h3>
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
        </div>
    </body>
</html>

