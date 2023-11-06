package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDAO;
import com.model.User;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // สร้างอ็อบเจ็กต์ UserDAO เมื่อ Servlet ถูกสร้างขึ้น
        if (userDAO == null) {
            throw new ServletException("UserDAO is not properly initialized.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            response.sendRedirect("login.html?error=user_not_found");
        } else if (BCrypt.checkpw(password, user.getPassword())) {
            response.sendRedirect("dashboard.html");
        } else {
            response.sendRedirect("login.html?error=incorrect_password");
        }
    }
}
