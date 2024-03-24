package com.eyogo.http.servlet;

import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.entity.Role;
import com.eyogo.http.service.UserService;
import com.eyogo.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("/login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(req.getContextPath() + "/login?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSuccess(GetUserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("useCascade", false);
        Integer selectedUserId = (Integer) req.getSession().getAttribute("selectedUserId");
        if (selectedUserId == null) {
            req.getSession().setAttribute("selectedUserId", user.getId());
        }
        if (user.getRole().equals(Role.ADMIN)) {
            resp.sendRedirect(req.getContextPath() + "/unit");
        } else {
            resp.sendRedirect(req.getContextPath() + "/unit");
        }
    }
}
