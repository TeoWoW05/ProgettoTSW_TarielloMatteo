package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.http.HttpSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Dao.DaoProdotto;
@WebServlet("/EliminaProdServlet")
public class EliminaProdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
private DaoProdotto prodottoDao;
    
    @Override
    public void init() throws ServletException {
        try {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            if (ds == null) {
                Context initCtx = new InitialContext();
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                ds = (DataSource) envCtx.lookup("jdbc/ProgettoTSW");
            }
            prodottoDao = new DaoProdotto(ds);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("ruolo"))) {
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
        }
        
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            
            // Prima elimina le categorie associate
            prodottoDao.removeAllCategorie(id);
            
            // Poi elimina il prodotto
            prodottoDao.doDelete(id);
            
            session.setAttribute("messaggioSuccesso", "Prodotto eliminato con successo!");
            
        } catch (Exception e) {
            session.setAttribute("errore", "Errore nell'eliminazione del prodotto");
        }
        
        response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
