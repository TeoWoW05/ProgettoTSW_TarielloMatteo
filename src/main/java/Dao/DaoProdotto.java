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
		this.dataSource = dataSource;
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
    public List<Prodotto> doRetrieveAll() throws SQLException {
        String sql = "SELECT * FROM Prodotto ORDER BY nome";
        List<Prodotto> prodotti = new ArrayList<>();
        
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
	    public List<Prodotto> doRetrieveByCategoria(String categoria) throws SQLException {
	        String sql = "SELECT p.* FROM Prodotto p " +
	                     "JOIN Possiede pos ON p.codice_prodotto = pos.prodotto_id " +
	                     "WHERE pos.categoria_nome = ? " +
	                     "ORDER BY p.nome";
	        List<Prodotto> prodotti = new ArrayList<>();
	        
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
	    public List<Prodotto> doRetrieveByNome(String nome) throws SQLException {
	        String sql = "SELECT * FROM Prodotto WHERE nome LIKE ? ORDER BY nome";
	        List<Prodotto> prodotti = new ArrayList<>();
	        
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
	    public List<Prodotto> doRetrieveByPrezzo(float min, float max) throws SQLException {
	        String sql = "SELECT * FROM Prodotto WHERE costo BETWEEN ? AND ? ORDER BY costo";
	        List<Prodotto> prodotti = new ArrayList<>();
	        
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
	
	
