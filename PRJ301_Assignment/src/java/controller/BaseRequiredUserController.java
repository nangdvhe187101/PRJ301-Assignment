/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import data.Features;
import data.Roles;
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
public abstract class BaseRequiredUserController extends HttpServlet{
    private User getAuthenticateUser(HttpServletRequest req){
        return (User) req.getSession().getAttribute("account");
    }
    
    //kiểm tra quyền 
    private boolean isAuthority(HttpServletRequest req, User u){
        String visit_url = req.getServletPath();
        for (Roles role : u.getRoles()) {
            for (Features features : role.getFeatures()) {
                if (features.getUrl().equals(visit_url)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        User user = getAuthenticateUser(req);
        if (user != null && isAuthority(req, user)) {
            doPost(req, resp, user);
        } else {
            resp.getWriter().println("access denied!");
        }
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, User user) 
            throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, User user) 
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        User user = getAuthenticateUser(req);
        if (user != null && isAuthority(req, user)) {
            doGet(req, resp, user);
        } else {
            resp.getWriter().println("access denied!");
        }
    }
}
