/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public abstract class BaseRequiredAuthenticationController extends HttpServlet{
    private User getAthenticatedUser(HttpServletRequest req)
    {
        return (User) req.getSession().getAttribute("user");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAthenticatedUser(req);
        if (user != null) {
            doPost(req, resp, user);
        }
        else{
            resp.getWriter().println("access denied!");
        }
    }
    
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAthenticatedUser(req);
        if (user != null) {
            doGet(req, resp, user);
        }
        else{
            resp.getWriter().println("access denied!");
        }
    }
    
}
