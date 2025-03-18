<%-- 
    Document   : Staff
    Created on : Mar 14, 2025, 10:00:02 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="data.LeaveRequests, java.util.List" %>
<%
    if (session == null || session.getAttribute("account") == null) {
        response.sendRedirect(request.getContextPath() + "/Login");
        return;
    }
    String userRole = (String) session.getAttribute("userRole");
    if (!"Staff".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/Login");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <title>Hệ Thống Quản Lý Nghỉ Phép</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            let currentMonth = new Date().getMonth() + 1;
            let currentYear = new Date().getFullYear();

            function showSection(sectionId) {
                let sections = ["calendar", "all-orders", "create-order"];
                sections.forEach(id => {
                    document.getElementById(id).style.display = "none";
                });
                document.getElementById(sectionId).style.display = "block";

                sessionStorage.setItem("activeTab", sectionId);
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
                document.getElementById("calendar-title").innerHTML = "Tháng " + currentMonth + " năm " + currentYear;
                generateCalendar();
            }

            function getStatus(status) {
                switch (status) {
                    case 0:
                        return 'Inprogress';
                    case 1:
                        return 'Rejected';
                    case 2:
                        return 'Approved';
                    default:
                        return 'Unknown';
                }
            }

            // Gộp các sự kiện DOMContentLoaded và xử lý modal
            document.addEventListener("DOMContentLoaded", function () {
                const activeTab = sessionStorage.getItem("activeTab");
                if (activeTab && activeTab !== "") {
                    showSection(activeTab);
                } else {
                    showSection('calendar'); // Luôn hiển thị "Trang chủ" mặc định
                    sessionStorage.setItem("activeTab", "calendar");
                }
                updateCalendar();
            });

            // Xử lý modal update
            $(document).ready(function () {
                $('#updateModal').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var id = button.data('id');
                    var title = button.data('title') || '';
                    var from = button.data('from') || '';
                    var to = button.data('to') || '';
                    var reason = button.data('reason') || '';
                    var modal = $(this);
                    modal.find('#leaveRequestId').val(id);
                    modal.find('#title').val(title);
                    modal.find('#startDate').val(from);
                    modal.find('#endDate').val(to);
                    modal.find('#reason').val(reason);
                });

                document.getElementById("updateForm").addEventListener("submit", function (event) {
                    const startDate = document.getElementById("startDate").value;
                    const endDate = document.getElementById("endDate").value;
                    const datePattern = /^\d{4}-\d{2}-\d{2}$/;

                    if (!startDate || !endDate || !datePattern.test(startDate) || !datePattern.test(endDate)) {
                        alert("Vui lòng nhập ngày theo định dạng yyyy-MM-dd (ví dụ: 2025-03-18)");
                        event.preventDefault();
                        return false;
                    }

                    const start = new Date(startDate);
                    const end = new Date(endDate);
                    if (start > end) {
                        alert("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
                        event.preventDefault();
                        return false;
                    }
                });

                // Chuyển tab khi có thông báo thành công
                if ("${success}" !== "") {
                    showSection('all-orders');
                }
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
                <h4>Chức vụ: Staff</h4>
                <a onclick="showSection('calendar')">🏠 Trang chủ</a>
                <a onclick="showSection('create-order')">✍️ Tạo đơn nghỉ phép</a>
                <a onclick="showSection('all-orders')">📦 Tất cả đơn đã tạo</a>
            </div>

            <div id="calendar" class="content">
                <h3>Chào mừng: ${sessionScope.displayName}</h3>
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
                        <% 
                        List<LeaveRequests> leaveRequests = (List<LeaveRequests>) request.getAttribute("leaveRequests");
                        if (leaveRequests != null && !leaveRequests.isEmpty()) {
                            for (LeaveRequests leaveRequest : leaveRequests) {
                                String fromDate = (leaveRequest.getFrom() != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(leaveRequest.getFrom()) : "";
                                String toDate = (leaveRequest.getTo() != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(leaveRequest.getTo()) : "";
                                String createdByUsername = (leaveRequest.getCreatedby() != null && leaveRequest.getCreatedby().getUsername() != null) ? leaveRequest.getCreatedby().getUsername() : "Unknown";
                                String statusText = "";
                                switch (leaveRequest.getStatus()) { 
                                    case 0: statusText = "Inprogress"; break;
                                    case 1: statusText = "Rejected"; break;
                                    case 2: statusText = "Approved"; break;
                                    default: statusText = "Unknown";
                                }
                                String processedBy = (leaveRequest.getProcessedByDisplayName() != null) ? leaveRequest.getProcessedByDisplayName() : "Chưa xử lý";
                        %>
                        <tr>
                            <td><%= leaveRequest.getTitle() != null ? leaveRequest.getTitle() : "" %></td> 
                            <td><%= fromDate %></td>
                            <td><%= toDate %></td>
                            <td><%= createdByUsername %></td>
                            <td><%= statusText %></td>
                            <td><%= processedBy %></td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal" 
                                        data-target="#updateModal" 
                                        data-id="<%= leaveRequest.getId() %>"
                                        data-title="<%= leaveRequest.getTitle() != null ? leaveRequest.getTitle() : "" %>"
                                        data-from="<%= fromDate %>"
                                        data-to="<%= toDate %>"
                                        data-reason="<%= leaveRequest.getReason() != null ? leaveRequest.getReason() : "" %>">
                                    Update
                                </button>
                                <form action="${pageContext.request.contextPath}/employee/delete" method="POST" style="display:inline;">
                                    <input type="hidden" name="id" value="<%= leaveRequest.getId() %>">
                                    <button type="submit" class="status-btn reject-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa đơn này?')">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="7">Bạn chưa tạo đơn nghỉ phép nào.</td>
                        </tr>
                        <%
                            }
                        %>
                        <c:if test="${not empty success}">
                        <div style="color: green; font-weight: bold; margin-top: 15px;">${success}</div>
                        <% session.removeAttribute("success-all"); %>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div style="color: red; font-weight: bold; margin-top: 15px;">${error}</div>
                        <% session.removeAttribute("error-all"); %>
                    </c:if>
                    </tbody>
                </table>
                <!-- Modal để cập nhật đơn nghỉ phép -->
                <div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateModalLabel">Cập Nhật Đơn Nghỉ Phép</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="updateForm" action="${pageContext.request.contextPath}/employee/update" method="POST" accept-charset="UTF-8">
                                    <input type="hidden" id="leaveRequestId" name="id">
                                    <div class="form-group">
                                        <label for="title">Tiêu đề:</label>
                                        <input type="text" class="form-control" id="title" name="title" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="startDate">Từ ngày:</label>
                                        <input type="date" class="form-control" id="startDate" name="startDate" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="endDate">Tới ngày:</label>
                                        <input type="date" class="form-control" id="endDate" name="endDate" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="reason">Lý do:</label>
                                        <textarea class="form-control" id="reason" name="reason" required></textarea>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="create-order" class="content" style="display: none;">
                <h2>Tạo Đơn Xin Nghỉ Phép</h2>
                <span><b>User:</b> ${sessionScope.displayName}</span>
                <span><b>Department:</b> Sales</span>
                <span><b>Role:</b> ${sessionScope.userRole}</span>
                <form action="${pageContext.request.contextPath}/employee/create" method="POST" class="leave-request-form">
                    <div class="form-group">
                        <label for="title">Tiêu đề:</label>
                        <input type="text" name="title" id="title" class="form-control" required style="width: 100%; padding: 10px; font-size: 16px;">
                    </div>
                    <div class="form-group">
                        <label for="startDate">Từ ngày:</label>
                        <input type="date" name="startDate" id="startDate" class="form-control" required style="width: 100%; padding: 10px; font-size: 16px;">
                    </div>
                    <div class="form-group">
                        <label for="endDate">Tới ngày:</label>
                        <input type="date" name="endDate" id="endDate" class="form-control" required style="width: 100%; padding: 10px; font-size: 16px;">
                    </div>
                    <div class="form-group">
                        <label for="reason">Lý do:</label>
                        <textarea name="reason" id="reason" class="form-control" required style="width: 100%; padding: 10px; font-size: 10px; height: 100px;"></textarea>
                    </div>
                    <div class="btn-group">
                        <button type="submit" class="custom-btn approve-btn" style="padding: 10px 20px; font-size: 16px;">Gửi</button>
                        <button type="reset" class="custom-btn reject-btn" style="padding: 10px 20px; font-size: 16px; margin-left: 20px;">Hủy</button>
                    </div>

                </form>
                <c:if test="${not empty success}">
                    <div style="color: green; font-weight: bold; margin-top: 15px;">${success}</div>
                    <% session.removeAttribute("success"); %>
                </c:if>
                <c:if test="${not empty error}">
                    <div style="color: red; font-weight: bold; margin-top: 15px;">${error}</div>
                    <% session.removeAttribute("error"); %>
                </c:if>
            </div>
        </div>
    </body>
</html>