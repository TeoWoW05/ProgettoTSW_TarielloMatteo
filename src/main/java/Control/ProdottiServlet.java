package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import Dao.DaoCategoria;
import Dao.DaoProdotto;
import Model.Categoria;
import Model.Prodotto;

@WebServlet("/ProdottiServlet")
public class ProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private DaoProdotto prodottoDao;
    private DaoCategoria categoriaDao;
    
    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile");
        }
        prodottoDao = new DaoProdotto(ds);
        categoriaDao = new DaoCategoria(ds);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String categoria = request.getParameter("categoria");
            String cerca = request.getParameter("cerca");
            
            ArrayList<Prodotto> prodotti;
            
            if (categoria != null && !categoria.isEmpty()) {
                
                prodotti = prodottoDao.doRetrieveByCategoria(categoria);
                request.setAttribute("categoriaSelezionata", categoria);
                
            } else if (cerca != null && !cerca.isEmpty()) {
               
                prodotti = prodottoDao.doRetrieveByNome(cerca);
                request.setAttribute("termineRicerca", cerca);
                
            } else {
               
                prodotti = prodottoDao.doRetrieveAll();
            }
            
            
            ArrayList<Categoria> categorie = categoriaDao.doRetrieveAll();
            request.setAttribute("categorie", categorie);
            
            
            Map<Integer, List<String>> categorieProdotti = new HashMap<>();
            for (Prodotto p : prodotti) {
                List<String> cats = prodottoDao.getCategorieProdotto(p.getCodiceProdotto());
                categorieProdotti.put(p.getCodiceProdotto(), cats);
                request.setAttribute("categorie_" + p.getCodiceProdotto(), cats);
            }
            
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("/WEB-INF/View/Prodotti.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nel caricamento dei prodotti");
            request.getRequestDispatcher("/WEB-INF/View/Error.jsp").forward(request, response);
        }
    }
}