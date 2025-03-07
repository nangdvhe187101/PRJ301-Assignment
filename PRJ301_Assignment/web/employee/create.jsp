<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
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
                <h3>Nhân Viên</h3>
                <a href="employeeInterface.jsp">🏠 Trang chủ</a>
                <a href="create.jsp">📝 Tạo đơn nghỉ phép</a>
                <a href="allOrders.jsp">📄 Tất cả đơn đã tạo</a>
                <a href="leave-requests.jsp">🏠 Xem tất cả đơn nghỉ phép</a>
                <a href="employee-status.jsp">📊 Xem tình trạng lao động</a>
            </div>

            <!-- Leave Form Content -->
            <div class="leave-form" id="leave-form">
                <h2>Đơn xin nghỉ phép</h2>
                <p>
                <p><b>User:</b> John Doe</p>
                <p><b>Role:</b> Employee</p>
                <p><b>Department:</b> Sales</p>
                </p>
                <form action="create" method="POST">
                    <label>Từ ngày:</label>
                    <input type="date" name="startDate" required>
                    <label>Tới ngày:</label>
                    <input type="date" name="endDate" required>
                    <label>Lý do</label>
                    <textarea name="reason" required></textarea>

                    <div class="btn-group">
                        <button class="btn" type="submit">Gửi</button>
                        <button class="btn" type="reset">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>