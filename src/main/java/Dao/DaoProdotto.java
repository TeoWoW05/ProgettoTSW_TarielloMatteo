package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import Model.Prodotto;

public class DaoProdotto implements DaoProdottoInterface {

	private DataSource dataSource;
	
	public DaoProdotto(DataSource datasource) {
		this.dataSource = datasource;
	}
	
	@Override
	public void doSave(Prodotto prodotto) throws SQLException{
		String sql ="INSERT INTO Prodotto (nome, descrizione, costo, immagine, quantita_magazzino) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				
				ps.setString(1, prodotto.getNome());
				ps.setString(2, prodotto.getDescrizione());
				ps.setFloat(3, prodotto.getCosto());
				ps.setString(4, prodotto.getImmagine());
				ps.setInt(5, prodotto.getQuantitaMagazzino());
				
				ps.executeUpdate();
				
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						prodotto.setCodiceProdotto(rs.getInt(1));
					}
				}
		     }
	      }
	
	@Override
    public void doUpdate(Prodotto prodotto) throws SQLException {
        String sql = "UPDATE Prodotto SET nome=?, descrizione=?, costo=?, immagine=?, quantita_magazzino=? WHERE codice_prodotto=?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setFloat(3, prodotto.getCosto());
            ps.setString(4, prodotto.getImmagine());
            ps.setInt(5, prodotto.getQuantitaMagazzino());
            ps.setInt(6, prodotto.getCodiceProdotto());
            
            ps.executeUpdate();
        }
    }
	
	@Override
	public void doDelete(int codiceProdotto) throws SQLException{
		String sql ="DELETE FROM Prodotto WHERE codice_prodotto=?";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, codiceProdotto);
			ps.executeUpdate();
		}
	}
	
	@Override
	public Prodotto doRetrieveByKey(int codiceProdotto) throws SQLException{
		String sql = "SELECT * FROM Prodotto WHERE codice_prodotto=?";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, codiceProdotto);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return creaProdotto(rs);
				}
			}
		}
		return null;
	}
	
	@Override
    public ArrayList<Prodotto> doRetrieveAll() throws SQLException {
        String sql = "SELECT * FROM Prodotto ORDER BY nome";
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                prodotti.add(creaProdotto(rs));
            }
        }
        return prodotti;
    }
	
	 @Override
	    public ArrayList<Prodotto> doRetrieveByCategoria(String categoria) throws SQLException {
	        String sql = "SELECT p.* FROM Prodotto p " +
	                     "JOIN Possiede pos ON p.codice_prodotto = pos.prodotto_id " +
	                     "WHERE pos.categoria_nome = ? " +
	                     "ORDER BY p.nome";
	        ArrayList<Prodotto> prodotti = new ArrayList<>();
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, categoria);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    prodotti.add(creaProdotto(rs));
	                }
	            }
	        }
	        return prodotti;
	    }
	 
	    @Override
	    public ArrayList<Prodotto> doRetrieveByNome(String nome) throws SQLException {
	        String sql = "SELECT * FROM Prodotto WHERE nome LIKE ? ORDER BY nome";
	        ArrayList<Prodotto> prodotti = new ArrayList<>();
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, "%" + nome + "%");
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    prodotti.add(creaProdotto(rs));
	                }
	            }
	        }
	        return prodotti;
	    }
	    
	    @Override
	    public ArrayList<Prodotto> doRetrieveByPrezzo(float min, float max) throws SQLException {
	        String sql = "SELECT * FROM Prodotto WHERE costo BETWEEN ? AND ? ORDER BY costo";
	        ArrayList<Prodotto> prodotti = new ArrayList<>();
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setFloat(1, min);
	            ps.setFloat(2, max);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    prodotti.add(creaProdotto(rs));
	                }
	            }
	        }
	        return prodotti;
	    }
	    
	    @Override
	    public void updateQuantita(int codiceProdotto, int quantita) throws SQLException {
	        String sql = "UPDATE Prodotto SET quantita_magazzino=? WHERE codice_prodotto=?";
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setInt(1, quantita);
	            ps.setInt(2, codiceProdotto);
	            ps.executeUpdate();
	        }
	    }
	    
	   @Override
	    public synchronized void saveCategoriaProdotto(int prodottoId, String categoriaNome) throws SQLException {
	        String sql = "INSERT INTO Possiede (prodotto_id, categoria_nome) VALUES (?, ?)";
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setInt(1, prodottoId);
	            ps.setString(2, categoriaNome);
	            ps.executeUpdate();
	        }
	    }

	   @Override
	    public synchronized ArrayList<String> getCategorieProdotto(int prodottoId) throws SQLException {
		   ArrayList<String> categorie = new ArrayList<>();
	        String sql = "SELECT categoria_nome FROM Possiede WHERE prodotto_id = ?";
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setInt(1, prodottoId);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    categorie.add(rs.getString("categoria_nome"));
	                }
	            }
	        }
	        return categorie;
	    }
	   
	   @Override
	    public synchronized void removeAllCategorie(int prodottoId) throws SQLException {
	        String sql = "DELETE FROM Possiede WHERE prodotto_id = ?";
	        
	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setInt(1, prodottoId);
	            ps.executeUpdate();
	        }
	    }

	   
	    
	     @Override
	    public synchronized void updateCategorieProdotto(int prodottoId, String[] categorieNomi) throws SQLException {
	        removeAllCategorie(prodottoId);
	        
	        if (categorieNomi != null) {
	            for (String categoriaNome : categorieNomi) {
	                saveCategoriaProdotto(prodottoId, categoriaNome);
	            }
	        }
	    }
	
	private Prodotto creaProdotto(ResultSet rs) throws SQLException {
        Prodotto prodotto = new Prodotto();
        prodotto.setCodiceProdotto(rs.getInt("codice_prodotto"));
        prodotto.setNome(rs.getString("nome"));
        prodotto.setDescrizione(rs.getString("descrizione"));
        prodotto.setCosto(rs.getFloat("costo"));
        prodotto.setImmagine(rs.getString("immagine"));
        prodotto.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
        return prodotto;
    }
}
	
	
