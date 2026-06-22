package Control;

import jakarta.servlet.RequestDispatcher; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Dao.DaoProdotto;
import Model.Prodotto;

@WebServlet("/HPServlet")
public class HPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DaoProdotto prodottoDao;
	
	@Override
	public void init() throws ServletException{
		try {
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			if(ds == null) {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				ds = (DataSource) envCtx.lookup("jdbc/ProgettoTSW");
			}
			prodottoDao = new DaoProdotto(ds);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Prodotto> tuttiProdotti = prodottoDao.doRetrieveAll();
			
			Collections.shuffle(tuttiProdotti);
			
			ArrayList<Prodotto> prodottiCarosello = new ArrayList<>();
			for(int i = 0; i<Math.min(8, tuttiProdotti.size()); i++) {
				prodottiCarosello.add(tuttiProdotti.get(i));
			}
			
			ArrayList<Prodotto> prodottiEvidenza = new ArrayList<>();
			for(int i = 8; i<Math.min(12, tuttiProdotti.size()); i++) {
				prodottiEvidenza.add(tuttiProdotti.get(i));
			}
			
			request.setAttribute("prodottiCarosello", prodottiCarosello);
			request.setAttribute("prodottiEvidenza", prodottiEvidenza);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/View/HomePage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
