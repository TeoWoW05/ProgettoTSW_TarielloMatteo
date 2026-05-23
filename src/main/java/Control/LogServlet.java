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

import java.sql.Connection;

import DatabaseConnection.DatabaseManager;
import Model.Utente;

/**
 * Servlet implementation class LogServlet
 */
@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validazione
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("errore", "Inserisci username/email e password!");
            request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
            return;
        }
        
        // Cerco l'utente nel database (per username o email)
        String sql = "SELECT * FROM Utente WHERE nickname = ? OR email = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, username);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Utente trovato, controllo password
                String dbPassword = rs.getString("password");
                
                // TODO: Confronto con hash della password!
                if (dbPassword.equals(password)) {
                    
                    // Login riuscito - Creo l'oggetto Utente
                    Utente utente = new Utente();
                    utente.setName(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setNickname(rs.getString("nickname"));
                    utente.setEmail(rs.getString("email"));
                    
                    // Salvo l'utente in sessione
                    HttpSession session = request.getSession();
                    session.setAttribute("utente", utente);
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("nickname", utente.getNickname());
                    
                    // Reindirizzo alla home o alla pagina precedente
                    String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
                    if (redirectUrl != null) {
                        session.removeAttribute("redirectAfterLogin");
                        response.sendRedirect(redirectUrl);
                    } else {
                        response.sendRedirect("/WEB-INF/View/HomePage.jsp");
                    }
                    
                } else {
                    // Password errata
                    request.setAttribute("errore", "Password errata!");
                    request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
                }
            } else {
                // Utente non trovato
                request.setAttribute("errore", "Username/email non trovato!");
                request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore del database: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/Login.jsp").forward(request, response);
        }
    }

}
