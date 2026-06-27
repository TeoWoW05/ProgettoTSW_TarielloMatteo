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
import Dao.DaoProdotto;
import Model.Carrello;
import Model.Ordine;
import Model.Prodotto;
import Model.Utente;


@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoOrdine ordineDao;
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
            ordineDao = new DaoOrdine(ds);
            prodottoDao = new DaoProdotto(ds);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("utente") == null) {
			session = request.getSession();
			
			String queryString = request.getQueryString();
			String redirectUrl = "/CheckoutServlet";
			if(queryString != null) {
				redirectUrl += "?" + queryString;
			}
			
			
			session.setAttribute("redirectAfterLogin", redirectUrl);
			response.sendRedirect(request.getContextPath() + "/LogServlet");
			return;
		}
		
		String action = request.getParameter("action");
		
		if("buynow".equals(action)) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				int qty = 1;
				if(request.getParameter("qty") != null) {
					qty = Integer.parseInt(request.getParameter("qty"));
				}
				
				session.setAttribute("buyNowId", id);
				session.setAttribute("buyNowQty", qty);
				request.setAttribute("acquistoDiretto", true);
				
				Prodotto prodotto = prodottoDao.doRetrieveByKey(id);
				request.setAttribute("prodottoDiretto", prodotto);
				request.setAttribute("buyNowQty", qty);
				request.setAttribute("totale", prodotto.getCosto() * qty);
				
			}catch (Exception e) {
	            response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
	            return;
	        }
			
		}else {
			
			Carrello carrello = (Carrello) session.getAttribute("carrello");
			if(carrello == null || carrello.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
				return;
			}
			request.setAttribute("acquistoDiretto", false);
		}
		
		request.getRequestDispatcher("/WEB-INF/View/Checkout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    HttpSession session = request.getSession(false);
	    
	    if (session == null || session.getAttribute("utente") == null) {
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
	    
	    String panPulito = pan.replaceAll("[^0-9]", "");
	    String cvvPulito = cvv.replaceAll("[^0-9]", "");
	    String capPulito = cap.replaceAll("[^0-9]", "");
	    
	    if (panPulito.length() != 16) {
	        session.setAttribute("erroreCheckout", "Il numero della carta deve essere di 16 cifre!");
	        response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
	        return;
	    }
	    
	    if (cvvPulito.length() != 3) {
	        session.setAttribute("erroreCheckout", "Il CVV deve essere di 3 cifre!");
	        response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
	        return;
	    }
	    
	    try {
	        Utente utente = (Utente) session.getAttribute("utente");
	        
	        float totale;
	        Integer buyNowId = (Integer) session.getAttribute("buyNowId");
	        Integer buyNowQty = (Integer) session.getAttribute("buyNowQty");
	        
	        if (buyNowId != null && buyNowQty != null) {
	            // Acquisto diretto: calcola dal prodotto
	            Prodotto prodotto = prodottoDao.doRetrieveByKey(buyNowId);
	            if (prodotto == null) {
	                session.setAttribute("erroreCheckout", "Prodotto non disponibile!");
	                response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
	                return;
	            }
	            totale = prodotto.getCosto() * buyNowQty;
	        } else {
	            // Acquisto da carrello
	            Carrello carrello = (Carrello) session.getAttribute("carrello");
	            if (carrello == null || carrello.isEmpty()) {
	                response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
	                return;
	            }
	            totale = carrello.getTotale();
	        }
	        
	        
	        
	        // Crea l'ordine
	        Ordine ordine = new Ordine();
	        ordine.setEmail_utente(utente.getEmail());
	        ordine.setTotale_ordine(totale);
	        ordine.setVia(via.trim());
	        ordine.setCivico(civico.trim());
	        ordine.setCap(Integer.parseInt(capPulito));
	        ordine.setCittà(citta.trim());
	        ordine.setPan(Long.parseLong(panPulito));
	        ordine.setScadenza(scadenza.trim());
	        ordine.setCVV(Integer.parseInt(cvvPulito));
	        ordine.setStato("In elaborazione");
	        
	        int progressivo = ordineDao.getProssimoProgressivo(utente.getEmail());
	        ordine.setNumeroProgressivo(progressivo);
	        
	        ordineDao.doSave(ordine);
	        
	        // ✅ Se è acquisto diretto, rimuovi solo i dati buynow (NON il carrello)
	        if (buyNowId != null) {
	            session.removeAttribute("buyNowId");
	            session.removeAttribute("buyNowQty");
	        } else {
	            // Se è acquisto da carrello, svuota il carrello
	            Carrello carrello = (Carrello) session.getAttribute("carrello");
	            if (carrello != null) {
	                carrello.svuota();
	                session.setAttribute("carrello", carrello);
	            }
	        }
	        
	        session.setAttribute("ordineCompletato", "Ordine #" + progressivo + " effettuato con successo!");
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
 