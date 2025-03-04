<%-- 
    Document   : employee-satus
    Created on : Mar 5, 2025, 1:28:14 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tình Trạng Lao Động</title>
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
            <a href="leave-requests.jsp">🏠 Xem tất cả đơn nghỉ phép</a>
            <a href="employee-status.jsp">📊 Xem tình trạng lao động</a>
        </div>

        <div class="content">
            <h3>Tình trạng lao động của nhân viên</h3>
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

