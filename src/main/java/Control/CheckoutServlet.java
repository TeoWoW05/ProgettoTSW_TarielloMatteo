package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Dao.DaoOrdine;
import Model.Carrello;
import Model.Ordine;
import Model.Utente;


@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoOrdine ordineDao;
	
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
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("utente") == null) {
			session = request.getSession();
			session.setAttribute("redirectAfterLogin", "/CheckoutServlet");
			response.sendRedirect(request.getContextPath() + "LogServlet");
			return;
		}
		
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		if(carrello == null || carrello.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/View/Checkout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("utente") == null) {
			response.sendRedirect(request.getContextPath() + "/LogServlet");
			return;
		}
		
request.setCharacterEncoding("UTF-8");
        
        // Dati spedizione
        String via = request.getParameter("via");
        String civico = request.getParameter("civico");
        String cap = request.getParameter("cap");
        String citta = request.getParameter("citta");
        
        // Dati carta
        String pan = request.getParameter("pan");
        String scadenza = request.getParameter("scadenza");
        String cvv = request.getParameter("cvv");
        
        // Validazione
        if (via == null || via.trim().isEmpty() ||
            civico == null || civico.trim().isEmpty() ||
            cap == null || cap.trim().isEmpty() ||
            citta == null || citta.trim().isEmpty() ||
            pan == null || pan.trim().isEmpty() ||
            scadenza == null || scadenza.trim().isEmpty() ||
            cvv == null || cvv.trim().isEmpty()) {
            
            session.setAttribute("erroreCheckout", "Tutti i campi sono obbligatori!");
            response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
            return;
        }
        
        if(pan.replace(" ", "").length()!= 16) {
        	session.setAttribute("erroreCheckout", "Il numero della carta deve essere di 16 cifre!");
        	response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
        	return;
        }
        
        if (cvv.length() != 3) {
            session.setAttribute("erroreCheckout", "Il CVV deve essere di 3 cifre!");
            response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
            return;
        }
        try {
            Utente utente = (Utente) session.getAttribute("utente");
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            
            // Crea l'ordine
            Ordine ordine = new Ordine();
            ordine.setEmail_utente(utente.getEmail());
            ordine.setTotale_ordine(carrello.getTotale());
            ordine.setVia(via);
            ordine.setCivico(civico);
            ordine.setCap(Integer.parseInt(cap));
            ordine.setCittà(citta);
            ordine.setPan(Integer.parseInt(pan.replace(" ", "")));
            ordine.setScadenza(scadenza);
            ordine.setCVV(Integer.parseInt(cvv));
            ordine.setStato("In elaborazione");
            
            // Salva l'ordine nel database
            ordineDao.doSave(ordine);
            
            // Svuota il carrello
            carrello.svuota();
            session.setAttribute("carrello", carrello);
            
            // Messaggio di successo
            session.setAttribute("ordineCompletato", "Ordine #" + ordine.getId() + " effettuato con successo!");
            response.sendRedirect(request.getContextPath() + "/CheckoutConfermaServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("erroreCheckout", "Errore nel salvataggio dell'ordine. Riprova.");
            response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
        } catch (NumberFormatException e) {
            session.setAttribute("erroreCheckout", "Formato dati non valido. Controlla i campi.");
            response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
        }
	}

}
 