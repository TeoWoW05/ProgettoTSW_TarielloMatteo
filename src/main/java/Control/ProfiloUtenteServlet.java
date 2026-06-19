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
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Dao.DaoUtente;
import Dao.DaoOrdine;
import Model.Utente;
import Model.Ordine;
import MetodoDiHashing.PasswordHashing;  // Importa la tua classe

@WebServlet("/ProfiloUtenteServlet")
public class ProfiloUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private DaoUtente utenteDao;
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
            utenteDao = new DaoUtente(ds);
            ordineDao = new DaoOrdine(ds);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
        }
        
        Utente utente = (Utente) session.getAttribute("utente");
        
        try {
            ArrayList<Ordine> ordini = ordineDao.doRetrieveByUtente(utente.getEmail());
            request.setAttribute("ordini", ordini);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/WEB-INF/View/Profilo.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
        }
        
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        Utente utente = (Utente) session.getAttribute("utente");
        
        try {
            if ("aggiornaProfilo".equals(action)) {
                
                String nome = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                String nickname = request.getParameter("nickname");
                
                if (nome == null || nome.trim().isEmpty() ||
                    cognome == null || cognome.trim().isEmpty() ||
                    nickname == null || nickname.trim().isEmpty()) {
                    
                    session.setAttribute("errore", "Nome, cognome e nickname sono obbligatori!");
                    response.sendRedirect(request.getContextPath() + "/profilo");
                    return;
                }
                
                if (!nickname.equals(utente.getNickname()) && utenteDao.existsByNickname(nickname)) {
                    session.setAttribute("errore", "Nickname già in uso!");
                    response.sendRedirect(request.getContextPath() + "/profilo");
                    return;
                }
                
                utente.setName(nome);
                utente.setCognome(cognome);
                utente.setNickname(nickname);
                
                utenteDao.doUpdate(utente);
                
                session.setAttribute("utente", utente);
                session.setAttribute("nickname", utente.getNickname());
                
                session.setAttribute("messaggioSuccesso", "Profilo aggiornato con successo!");
                response.sendRedirect(request.getContextPath() + "/profilo");
                
            } else if ("cambiaPassword".equals(action)) {
                
                String vecchiaPassword = request.getParameter("vecchiaPassword");
                String nuovaPassword = request.getParameter("nuovaPassword");
                String confermaPassword = request.getParameter("confermaPassword");
                
                if (vecchiaPassword == null || vecchiaPassword.isEmpty() ||
                    nuovaPassword == null || nuovaPassword.isEmpty() ||
                    confermaPassword == null || confermaPassword.isEmpty()) {
                    
                    session.setAttribute("errorePassword", "Tutti i campi sono obbligatori!");
                    response.sendRedirect(request.getContextPath() + "/profilo");
                    return;
                }
                
                
                if (!PasswordHashing.checkPassword(vecchiaPassword, utente.getPass())) {
                    session.setAttribute("errorePassword", "Password attuale errata!");
                    response.sendRedirect(request.getContextPath() + "/profilo");
                    return;
                }
                
                if (!nuovaPassword.equals(confermaPassword)) {
                    session.setAttribute("errorePassword", "Le nuove password non coincidono!");
                    response.sendRedirect(request.getContextPath() + "/profilo");
                    return;
                }
                
                if (nuovaPassword.length() < 6) {
                    session.setAttribute("errorePassword", "Minimo 6 caratteri!");
                    response.sendRedirect(request.getContextPath() + "/profilo");
                    return;
                }
                
               
                String nuovoHash = PasswordHashing.hashPassword(nuovaPassword);
                utente.setPass(nuovoHash);
                utenteDao.doUpdate(utente);
                
                session.setAttribute("utente", utente);
                session.setAttribute("messaggioSuccesso", "Password cambiata con successo!");
                response.sendRedirect(request.getContextPath() + "/profilo");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errore", "Errore nell'aggiornamento");
            response.sendRedirect(request.getContextPath() + "/profilo");
        }
    }
}
