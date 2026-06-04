package Filters;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean isAdmin = false;
        
        if (session != null) {
            String ruolo = (String) session.getAttribute("ruolo");
            isAdmin = "admin".equals(ruolo);
        }
        
        if (isAdmin) {
            // È admin, continua
            chain.doFilter(request, response);
        } else {
            // Non è admin, reindirizza alla home
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/HPServlet");
        }
    }
}
