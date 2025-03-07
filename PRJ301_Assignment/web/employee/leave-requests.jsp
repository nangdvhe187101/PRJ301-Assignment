<%-- 
    Document   : leave-requests
    Created on : Mar 5, 2025, 1:29:21 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Đơn Nghỉ Phép</title>
        <link rel="stylesheet" href="/styles.css">
    </head>
    <body>
        <div class="navbar">
            <h2>Hệ Thống Quản Lý Nghỉ Phép - Quản Lý</h2>
            <button class="logout">Đăng xuất</button>
        </div>

        <div class="main-container">
            <div class="sidebar">
                <h3>Quản Lý</h3>
                <a href="employeeInterface.jsp">🏠 Trang chủ</a>
                <a href="create.jsp">📝 Tạo đơn nghỉ phép</a>
                <a href="allOrders.jsp">📄 Tất cả đơn đã tạo</a>
                <a href="leave-requests.jsp">🏠 Xem tất cả đơn nghỉ phép</a>
                <a href="employee-status.jsp">📊 Xem tình trạng lao động</a>
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

