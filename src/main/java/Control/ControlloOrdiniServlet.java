package Control;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Dao.DaoOrdine;
import Dao.DaoUtente;
import Model.Ordine;
import Model.Utente;


@WebServlet("/ControlloOrdiniServlet")
public class ControlloOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoOrdine ordineDao;
	private DaoUtente utenteDao;
	
	@Override
	public void init() throws ServletException {
        try {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            if (ds == null) {
                Context initCtx = new InitialContext();
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                ds = (DataSource) envCtx.lookup("jdbc/ProgettoTSW");
            }
            ordineDao = new DaoOrdine(ds);
            utenteDao = new DaoUtente(ds);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("utente") == null) {
			response.sendRedirect(request.getContextPath() + "/LogServlet");
			return;
		}
		
		Utente utente = (Utente) session.getAttribute("utente");
		if(!utente.isAdmin()) {
			response.sendRedirect(request.getContextPath()+"/");
			return;
		}
		
		 String dataDa = request.getParameter("dataDa");
	        String dataA = request.getParameter("dataA");
	        String emailCliente = request.getParameter("emailCliente");
	        
	        try {
	            ArrayList<Ordine> ordini;
	            
	            // Filtro per cliente
	            if (emailCliente != null && !emailCliente.trim().isEmpty()) {
	                ordini = ordineDao.doRetrieveByUtente(emailCliente.trim());
	                
	            // Filtro per data
	            } else if (dataDa != null && !dataDa.isEmpty() && dataA != null && !dataA.isEmpty()) {
	                ordini = ordineDao.doRetrieveByDateRange(dataDa, dataA);
	                
	            // Tutti gli ordini
	            } else {
	                ordini = ordineDao.doRetrieveAll();
	            }
	            
	            // Filtra manualmente se abbiamo sia data che cliente
	            if (emailCliente != null && !emailCliente.trim().isEmpty() &&
	                dataDa != null && !dataDa.isEmpty() && dataA != null && !dataA.isEmpty()) {
	                // Filtra per entrambi
	                ArrayList<Ordine> ordiniCliente = ordineDao.doRetrieveByUtente(emailCliente.trim());
	                ArrayList<Ordine> ordiniData = ordineDao.doRetrieveByDateRange(dataDa, dataA);
	                
	                // Intersezione: ordini che sono in entrambe le liste
	                ordiniCliente.retainAll(ordiniData);
	                ordini = ordiniCliente;
	            }
	            
	            request.setAttribute("ordini", ordini);
	            request.setAttribute("dataDa", dataDa);
	            request.setAttribute("dataA", dataA);
	            request.setAttribute("emailCliente", emailCliente);
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            request.setAttribute("errore", "Errore nel caricamento ordini");
	        }
	        
	        request.getRequestDispatcher("/WEB-INF/View/Admin/Ordini.jsp").forward(request, response);
	    }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
