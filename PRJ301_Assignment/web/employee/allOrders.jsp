<%-- 
    Document   : allOrders
    Created on : Mar 7, 2025, 10:40:57 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
        <title>Tất cả đơn đã tạo</title>
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
        </div>
    </body>
</html>
