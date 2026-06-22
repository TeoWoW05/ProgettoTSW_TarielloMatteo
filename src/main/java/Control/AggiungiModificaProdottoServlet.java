package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import Dao.DaoCategoria;
import Dao.DaoProdotto;
import Model.Categoria;
import Model.Prodotto;

@WebServlet("/AggiungiModificaProdottoServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 15     // 15 MB
)
public class AggiungiModificaProdottoServlet extends HttpServlet {
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
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("ruolo"))) {
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
        }
        
        try {
            // Carica le categorie (servono sia per aggiunta che modifica)
            ArrayList<Categoria> tutteCategorie = categoriaDao.doRetrieveAll();
            request.setAttribute("categorie", tutteCategorie);
            
            String idStr = request.getParameter("id");
            
            if (idStr != null && !idStr.trim().isEmpty()) {
                
                int id = Integer.parseInt(idStr);
                Prodotto prodotto = prodottoDao.doRetrieveByKey(id);
                
                if (prodotto == null) {
                    response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
                    return;
                }
                
                ArrayList<String> categorieProdotto = prodottoDao.getCategorieProdotto(id);
                
                request.setAttribute("prodotto", prodotto);
                request.setAttribute("categorieProdotto", categorieProdotto);
                request.setAttribute("modifica", true);  // Flag per la JSP
                
            } else {
                // === AGGIUNTA ===
                request.setAttribute("modifica", false);  // Flag per la JSP
            }
            
            request.getRequestDispatcher("/WEB-INF/View/AggMod.jsp").forward(request, response);
            
        } catch (SQLException e) {
            request.setAttribute("errore", "Errore nel caricamento");
            request.getRequestDispatcher("/WEB-INF/View/Error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verifica admin
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("ruolo"))) {
            response.sendRedirect(request.getContextPath() + "/LogServlet");
            return;
        }
        
        String idStr = request.getParameter("id");        
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String costoStr = request.getParameter("costo");
        String quantitaStr = request.getParameter("quantita");
        String[] categorieSelezionate = request.getParameterValues("categorie");
        
        // Validazione
        if (nome == null || nome.trim().isEmpty() ||
            descrizione == null || descrizione.trim().isEmpty() ||
            costoStr == null || costoStr.trim().isEmpty() ||
            quantitaStr == null || quantitaStr.trim().isEmpty() ||
            categorieSelezionate == null || categorieSelezionate.length == 0) {
            
            request.setAttribute("errore", "Tutti i campi sono obbligatori!");
            doGet(request, response);
            return;
        }
        
        try {
            float costo = Float.parseFloat(costoStr);
            int quantita = Integer.parseInt(quantitaStr);
            
            // Gestione upload immagine
            Part filePart = request.getPart("immagine");
            String imagePath = null;
            
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + "immagini" + File.separator + "prodotti";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);
                imagePath = fileName;
            }
            
            if (idStr != null && !idStr.trim().isEmpty()) {
                // === MODIFICA PRODOTTO ESISTENTE ===
                int id = Integer.parseInt(idStr);
                Prodotto prodotto = prodottoDao.doRetrieveByKey(id);
                
                if (prodotto == null) {
                    response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
                    return;
                }
                
                prodotto.setNome(nome);
                prodotto.setDescrizione(descrizione);
                prodotto.setCosto(costo);
                prodotto.setQuantitaMagazzino(quantita);
                
                // Aggiorna immagine solo se ne è stata caricata una nuova
                if (imagePath != null) {
                    prodotto.setImmagine(imagePath);
                }
                
                prodottoDao.doUpdate(prodotto);
                
                // Aggiorna categorie
                if (categorieSelezionate != null) {
                    prodottoDao.updateCategorieProdotto(id, categorieSelezionate);
                }
                
                session.setAttribute("messaggioSuccesso", "Prodotto modificato con successo!");
                response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
                
            } else {
            
            // Crea e salva il prodotto
            Prodotto prodotto = new Prodotto();
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setCosto(costo);
            prodotto.setImmagine(imagePath);
            prodotto.setQuantitaMagazzino(quantita);
            
            prodottoDao.doSave(prodotto);
            
            // Salva le categorie
            for (String cat : categorieSelezionate) {
                prodottoDao.saveCategoriaProdotto(prodotto.getCodiceProdotto(), cat);
            }
            
            session.setAttribute("messaggioSuccesso", "Prodotto aggiunto con successo!");
            response.sendRedirect(request.getContextPath() + "/ProdottiServlet");
           }
            
        } catch (Exception e) {
            request.setAttribute("errore", "Errore: " + e.getMessage());
            doGet(request, response);
        }
    }
    
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "prodotto_" + System.currentTimeMillis() + ".jpg";
    }
}
