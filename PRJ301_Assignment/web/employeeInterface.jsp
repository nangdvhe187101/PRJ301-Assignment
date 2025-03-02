<%-- 
    Document   : employ
    Created on : Feb 22, 2025, 11:46:47 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            body
            {
                font-family: sans-serif;
                margin: 0;
                background: #eef2f7;
            }

            .navbar
            {
                background: #007bff;
                padding: 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                color: white;
            }

            .logout
            {
                background: white;
                border: 1px none black;
                padding: 10px 15px;
                border-radius: 5px;
                font-size: 15px;
            }

            .main-container
            {
                display: flex;
                padding: 20px;
            }

            .sidebar
            {
                width: 250px;
                background: #007bff;
                color: white;
                padding: 20px;
                flex-shrink: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                border-radius: 5px;
            }

            .sidebar a
            {
                color: white;
                text-decoration: none;
                display: block;
                border-radius: 5px;
                align-items: center;
                padding: 12px 15px;
                margin-bottom: 5px;

            }

            .content
            {
                flex-grow: 1;
                background: white;
                padding: 20px;
                border-radius: 10px;
                margin-left: 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .calendar
            {
                margin-top: 20px;
                background: #f9f9f9;
                padding: 20px;
                border-radius: 10px;
            }

            .calendar-header
            {
                display: flex;
                justify-content: space-between;
                align-content: center;
            }

            .calendar-header button
            {
                padding: 8px 12px;
                border: none;
                border-radius: 5px;
                background:  #007bff;
                color: white;
            }

            .calendar-table
            {
                width: 100%;
                margin-top: 10px;
                border-collapse: collapse;
            }

            .calendar-table th, .calendar-table td
            {
                border: 1px solid #ccc;
                padding: 10px;
                text-align: center;
                width: 14%;
            }

            .calendar-table th
            {
                background: #007bff;
                color: white;
            }


            /**********/


            .leave-form
            {
                flex: 1;
                background: white;
                padding: 20px;
                
                border-radius: 10px;
                display: none;
                margin-left: 20px;
            }

            .leave-form h2
            {
                margin-bottom: 15px;
            }

            .leave-form p
            {
                margin-bottom: 10px;
                font-size: 15px;
            }

            .leave-form label
            {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }

            .leave-form input, .leave-form textarea
            {
                width: 60%;
                padding: 10px;
                margin-top: 5px;
                font-size: 15px;
                border: 1px solid ;
                border-radius: 10px;
            }

            .btn-group
            {
                display: flex;
                margin-top: 20px;
                
            }

            .btn
            {
                padding: 15px 20px;
                border: none;
                font-size: 15px;
                border-radius: 5px;
                background: #007bff;
                color: white;
                margin: 0 10px;
            }
        </style>

        <script>
            function showForm()
            {
                document.querySelector(".content").style.display = "none";
                document.getElementById('leave-form').style.display = "block";
            }
            
            function showHome()
            {
                document.querySelector(".content").style.display = "block";
                document.getElementById('leave-form').style.display = "none";
            }
        </script>
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
                <h3>Nhân Viên</h3>
                <a onclick="showHome()">🏠 Trang chủ</a>
                <a onclick="showForm()">📝 Tạo đơn nghỉ phép</a>
                <a href="">📄 Tất cả đơn đã tạo</a>
            </div>
            
            <div class="content">
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
            
            <!--creatOrder-->
            <div class="leave-form" id="leave-form">
                <h2>Đơn xin nghỉ phép</h2>
                <p>
                    <b>User</b> ,
                    <b>Role</b> ,
                    <b>Dep</b> ,
                </p>
                <form>
                    <label>Từ ngày:</label>
                    <input type="date">
                    <label>Tới ngày:</label>
                    <input type="date">
                    <label>Lý do</label>
                    <textarea></textarea>

                    <div class="btn-group">
                        <button class="btn" type="submit">Gửi</button>
                        <button class="btn" type="reset">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
