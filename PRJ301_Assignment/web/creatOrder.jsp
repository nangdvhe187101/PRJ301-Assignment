<%-- 
    Document   : creatOrder
    Created on : Feb 23, 2025, 2:09:24 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo Đơn</title>
    </head>
    <style>
        .leave-form
        {
            padding: 20px;
            margin-top: 20px;
            border-radius: 10px;
            display: none;
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
            width: 100%;
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
            justify-content: space-evenly;
            
        }
        
        .btn
        {
            padding: 15px 20px;
            border: none;
            font-size: 15px;
            border-radius: 5px;
            background: #007bff;
            color: white;
        }
    </style>
    <body>
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
    </body>
</html>
