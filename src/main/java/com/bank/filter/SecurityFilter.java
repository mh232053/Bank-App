package com.bank.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    // Helper: Paths that are accessible without login
    private static final String[] PUBLIC_PATHS = {
        "/login",
        "/register",
        "/assets",
        "/index.jsp"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Prevent caching of sensitive pages
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
        httpResponse.setDateHeader("Expires", 0); // Proxies

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Allow public assets (css, js, images)
        if (path.startsWith("/assets/")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow explicitly public pages
        for (String publicPath : PUBLIC_PATHS) {
            if (path.equals(publicPath) || path.equals("/")) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Check Session
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            // Logged in, allow access
            // (Optional: Redirect logged-in users away from login page to dashboard)
            if (path.equals("/login")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard");
                return;
            }
            chain.doFilter(request, response);
        } else {
            // Not logged in, redirect to login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}
