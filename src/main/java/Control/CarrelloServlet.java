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

import Dao.DaoProdotto;
import Model.Carrello;
import Model.CarrelloItem;
import Model.Prodotto;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
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
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }
        
        try {
            if ("aggiungi".equals(action)) {
                
                int id = Integer.parseInt(request.getParameter("id"));
                int qty = 1;
                if (request.getParameter("qty") != null) {
                    qty = Integer.parseInt(request.getParameter("qty"));
                }
                
                Prodotto prodotto = prodottoDao.doRetrieveByKey(id);
                if (prodotto != null) {
                    boolean trovato = false;
                    for (CarrelloItem item : carrello.getItems()) {
                        if (item.getProdotto().getCodiceProdotto() == id) {
                            trovato = true;
                            break;
                        }
                    }
                    
                    carrello.aggiungiProdotto(prodotto, qty);
                    session.setAttribute("carrello", carrello);
                    
                    if (trovato) {
                        session.setAttribute("messaggioSuccesso", "Quantità aggiornata!");
                    } else {
                        session.setAttribute("messaggioSuccesso", "Prodotto aggiunto!");
                    }
                }
                response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
                
            } else if ("rimuovi".equals(action)) {
                
                int id = Integer.parseInt(request.getParameter("id"));
                carrello.rimuoviProdotto(id);
                session.setAttribute("messaggioSuccesso", "Prodotto rimosso dal carrello!");
                response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
                
            } else if ("aggiorna".equals(action)) {
                
                int id = Integer.parseInt(request.getParameter("id"));
                int qty = Integer.parseInt(request.getParameter("qty"));
                carrello.aggiornaQuantita(id, qty);
                response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
                
            } else if ("clear".equals(action)) {
                
                carrello.svuota();
                session.setAttribute("messaggioSuccesso", "Carrello svuotato!");
                response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
                
            } else {
                
                request.getRequestDispatcher("/WEB-INF/View/Carrello.jsp").forward(request, response);
                
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nel caricamento del prodotto");
            request.getRequestDispatcher("/WEB-INF/View/Error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
