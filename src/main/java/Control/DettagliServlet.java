package Control;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import Dao.DaoProdotto;
import Model.Prodotto;

@WebServlet("/DettagliServlet")
public class DettagliServlet extends HttpServlet {
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            Prodotto prodotto = prodottoDao.doRetrieveByKey(id);
            
            if (prodotto == null) {
                request.setAttribute("errore", "Prodotto non trovato");
                request.getRequestDispatcher("/WEB-INF/View/Error.jsp").forward(request, response);
                return;
            }
            
            // Recupera le categorie del prodotto
            ArrayList<String> categorie = prodottoDao.getCategorieProdotto(id);
            
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("categorie", categorie);
            
            request.getRequestDispatcher("/WEB-INF/View/DettaglioProdotto.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nel caricamento del prodotto");
            request.getRequestDispatcher("/WEB-INF/View/Error.jsp").forward(request, response);
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
