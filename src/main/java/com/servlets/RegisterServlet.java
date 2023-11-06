package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDAOInterface;
import com.dao.UserDAOFactory;
import com.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAOInterface userDAO = UserDAOFactory.getUserDAO();

        boolean isRegistrationSuccessful = false;

        if (!userDAO.isUsernameTaken(username)) {
            User user = new User(username, password);
            user.setUsername(username);
            user.setPassword(password);

            userDAO.addUser(user);

            isRegistrationSuccessful = true;
        }

        if (isRegistrationSuccessful) {
            response.sendRedirect("login.html");
        } else {
            response.sendRedirect("register.html?error=username_taken");
        }
    }
}
