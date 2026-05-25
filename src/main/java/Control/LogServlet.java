package Control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import java.sql.Connection;

import DatabaseConnection.DatabaseManager;
import Model.Utente;
import Dao.DaoUtenteInterface;
import Dao.DaoUtente;


@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUtenteInterface utenteDao;
	
	 @Override
	    public void init() throws ServletException {
	        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	        if (ds == null) {
	            throw new ServletException("DataSource non disponibile nel contesto");
	        }
	        utenteDao = new DaoUtente(ds);
	    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("registrazioneCompletata")!= null) {
			request.setAttribute("messaggioSuccesso","Registrazione effettuata con successo! Ora puoi accedere.");
			
			session.removeAttribute("registrazioneCompletata");
		}
		
		request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validazione
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("errore", "Inserisci email e password!");
            request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
            return;
        }
        
        try {
            Utente utente = utenteDao.doRetrieveByKey(email);
            
            if (utente != null && utente.getPass().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);
                session.setAttribute("loggedIn", true);
                session.setAttribute("email", utente.getEmail());
                session.setAttribute("nickname", utente.getNickname());
                
                request.getRequestDispatcher("/WEB-INF/View/HomePage.jsp").forward(request, response);
            } else {
                request.setAttribute("errore", "Username/email o password errati");
                request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore del database: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
        }
    }

}
