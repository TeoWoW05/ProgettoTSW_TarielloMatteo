package Control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
		
		
		
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/Registrazione.jsp");
		dispatcher.forward(request, response);
	}

}
