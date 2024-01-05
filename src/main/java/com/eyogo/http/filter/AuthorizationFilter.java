package com.eyogo.http.filter;

import com.eyogo.http.dto.GetUserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        if (requestURI.equals("/login") || requestURI.equals("/registration") || isUserLoggedIn(servletRequest)) {//TODO change to admin whitelist
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        GetUserDto user = (GetUserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }
}
