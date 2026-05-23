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

import DatabaseConnection.DatabaseManager;

@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
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
			request.getRequestDispatcher("/View/Registrazione.jsp").forward(request, response);
			
			return;
		}
		
		if(!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			request.setAttribute("Errore", "Email non valida!");
			request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
			
			return;
		}
		
		try(Connection conn = DatabaseManager.getConnection()){
			
			String controlloNickname = "SELECT * FROM Utente WHERE nickname = ?";
			try(PreparedStatement p = conn.prepareStatement(controlloNickname)){
				p.setString(1, controlloNickname);
				ResultSet rs = p.executeQuery();
				if(rs.next()) {
					request.setAttribute("Errore","Nickname già in uso!");
					request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
					
					return;
				}
			}
			
			String controlloEmail = "SELECT * FROM Utente WHERE email = ?";
            try (PreparedStatement p = conn.prepareStatement(controlloEmail)) {
                p.setString(1, email);
                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    request.setAttribute("Errore", "Email già registrata!");
                    request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
                    return;
                }
            }
            
            String sql = "INSERT INTO Utente (email, nome, cognome, nickname, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.setString(1, email);
                p.setString(2, nome);
                p.setString(3, cognome);
                p.setString(4, nickname);
                p.setString(5, password); // TODO: Hash della password!
                
                int righeInserite = p.executeUpdate();
                
                if (righeInserite > 0) {
                    // Registrazione riuscita
                    response.sendRedirect("/WEB-INF/View/Login.jsp?registrato=true");
                } else {
                    request.setAttribute("Errore", "Errore durante la registrazione");
                    request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
                }
            }
            
		}catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("Errore", "Errore del database: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp").forward(request, response);
        }
		
	}

}
