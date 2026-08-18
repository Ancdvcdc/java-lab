package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bài 11: Ghi log truy cập tập trung.
 * Ghi ra console: URI, method, user (nếu đã đăng nhập) và thời gian truy cập.
 */
@WebFilter(urlPatterns = {"/*"})
public class LogFilter implements Filter {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("LogFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI();
        String method = req.getMethod();

        HttpSession session = req.getSession(false);
        String user = (session != null && session.getAttribute("username") != null)
                ? (String) session.getAttribute("username")
                : "anonymous";

        String time = LocalDateTime.now().format(FORMAT);

        System.out.println("[LOG] " + time + " | " + method + " " + uri + " | user=" + user);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("LogFilter destroyed");
    }
}
