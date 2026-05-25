package Control;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import DatabaseConnection.DatabaseManager;

import Dao.DaoUtente;
import Dao.DaoUtenteInterface;
import Model.Utente;

@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUtenteInterface utenteDao;
	
	@Override
	public void init() throws ServletException{
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		if(ds == null) {
			throw new ServletException("DataSource non disponibile nel contesto");
		}
		utenteDao = new DaoUtente(ds);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String confermaPass = request.getParameter("passCon");
		
		if(nome == null || nome.trim().isEmpty() ||
		   cognome == null || cognome.trim().isEmpty() ||
		   nickname == null || nickname.trim().isEmpty() ||
		   email == null || email.trim().isEmpty() ||
		   password == null || password.trim().isEmpty()) {
			
			request.setAttribute("Errore","Tutti i campi sono obbligatori!");
			request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
			
			return;
		}
		
		if(!password.equals(confermaPass)) {
			request.setAttribute("Errore", "Le password non coincidono!");
			request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
			
			return;
		}
		
		if(password.length() < 6) {
			request.setAttribute("Errore", "La password deve essere di almeno 6 caratteri!");
			request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
			
			return;
		}
		
		if(!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			request.setAttribute("Errore", "Email non valida!");
			request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
			
			return;
		}
		
		try {
            // Verifica se email esiste già (chiave primaria)
            if (utenteDao.existsByEmail(email)) {
                request.setAttribute("Errore", "Email già registrata!");
                request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
                return;
            }
            
            // Verifica se nickname esiste già
            if (utenteDao.existsByNickname(nickname)) {
                request.setAttribute("Errore", "Nickname già in uso!");
                request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
                return;
            }
            
            // Crea e salva l'utente
            Utente utente = new Utente();
            utente.setEmail(email);
            utente.setName(nome);
            utente.setCognome(cognome);
            utente.setNickname(nickname);
            utente.setPass(password);
            
            utenteDao.doSave(utente);
            
            request.getSession().setAttribute("registrazioneCompletata",true);
            
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("Errore", "Errore del database: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
            return;
        }
    }
    
}
