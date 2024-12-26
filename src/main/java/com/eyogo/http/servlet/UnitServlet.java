package com.eyogo.http.servlet;

import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.entity.Role;
import com.eyogo.http.service.UnitService;
import com.eyogo.http.service.UserService;
import com.eyogo.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

@WebServlet("/unit")
public class UnitServlet extends HttpServlet {

    @Autowired
    private UnitService unitService;
    @Autowired
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("units", unitService.findAll());
        req.setAttribute("usersSelection", userService.findAll().stream()
                .sorted(Comparator.comparing(UserReadDto::getFirstName))
                .collect(Collectors.toList()));

        req.getRequestDispatcher(JspHelper.getPath("unit"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserReadDto user = ((UserReadDto) req.getSession().getAttribute("user"));
        if (user.getRole().equals(Role.ADMIN)) {
            String userToWatch = req.getParameter("userToWatch");
            if (StringUtils.isNotBlank(userToWatch)) {
                req.getSession().setAttribute("selectedUserId", Integer.valueOf(userToWatch));
            }
        }
        String useCascadeString = req.getParameter("useCascade");
        if (StringUtils.isNotBlank(useCascadeString)) {
            boolean useCascade = Boolean.parseBoolean(useCascadeString);
            if (!req.getSession().getAttribute("useCascade").equals(useCascade)) {
                req.getSession().setAttribute("useCascade", useCascade);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/unit");
    }
}
