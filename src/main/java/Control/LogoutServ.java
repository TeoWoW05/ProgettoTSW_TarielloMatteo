package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServ")
public class LogoutServ extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
      
        HttpSession session = request.getSession(false);
        
        if (session != null) {
           
            session.removeAttribute("utente");
            session.removeAttribute("loggedIn");
            session.removeAttribute("email");
            session.removeAttribute("nickname");
            
      
            session.invalidate();
        }
        
       
        response.sendRedirect(request.getContextPath() + "/LogServlet");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}