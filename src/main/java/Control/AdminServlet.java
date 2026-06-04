package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        

        String ruolo = (String) request.getSession().getAttribute("ruolo");
        if (!"admin".equals(ruolo)) {
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
        }
        
        // Recupera statistiche per la dashboard
        // int numUtenti = utenteDao.countAll();
        // int numProdotti = prodottoDao.countAll();
        // int numOrdini = ordineDao.countAll();
        
        request.getRequestDispatcher("/WEB-INF/View/...").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    		doGet(request,response);
    }
}