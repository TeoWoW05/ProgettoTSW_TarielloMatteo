package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import Model.Ordine;


public class DaoOrdine implements DaoOrdiniInterface{
	
	private DataSource ds;
	private static final String TABLE_NAME="Ordine";
	
	public DaoOrdine(DataSource ds) {
		this.ds = ds;
	}
	
	public void doSave(Ordine ordine) throws SQLException{
		String sql = "INSERT INTO " + TABLE_NAME + " (email_utente, totale_ordine, civico, cap, città, via, CVV, pan, scadenza, stato)"
				+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            
	            ps.setString(1, ordine.getEmail_utente());
	            ps.setFloat(2, ordine.getTotale_ordine());
	            ps.setString(3, ordine.getCivico());
	            ps.setInt(4, ordine.getCap());
	            ps.setString(5, ordine.getCittà());
	            ps.setString(6, ordine.getVia());
	            ps.setInt(7, ordine.getCVV());
	            ps.setInt(8, ordine.getPan());
	            ps.setString(9, ordine.getScadenza());         
	            ps.setString(10, ordine.getStato() != null ? ordine.getStato() : "In elaborazione");
	            
	            ps.executeUpdate();
	            
	            try (ResultSet rs = ps.getGeneratedKeys()) {
	                if (rs.next()) {
	                    ordine.setId(rs.getInt(1));
	                }
	            }
	        }
	    }
	 @Override
	    public synchronized Ordine doRetrieveByKey(int id) throws SQLException {
	        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
	        
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setInt(1, id);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return creaOrdine(rs);
	                }
	            }
	        }
	        return null;
	    }
	 
	 @Override
	    public synchronized ArrayList<Ordine> doRetrieveByUtente(String email) throws SQLException {
	        ArrayList<Ordine> ordini = new ArrayList<>();
	        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email_utente = ? ORDER BY data_ordine DESC";
	        
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, email);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    ordini.add(creaOrdine(rs));
	                }
	            }
	        }
	        return ordini;
	    }
	    
	    @Override
	    public synchronized ArrayList<Ordine> doRetrieveAll() throws SQLException {
	        ArrayList<Ordine> ordini = new ArrayList<>();
	        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY data_ordine DESC";
	        
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            
	            while (rs.next()) {
	                ordini.add(creaOrdine(rs));
	            }
	        }
	        return ordini;
	    }
	    
	    @Override
	    public synchronized void updateStato(int id, String stato) throws SQLException {
	        String sql = "UPDATE " + TABLE_NAME + " SET stato = ? WHERE id = ?";
	        
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, stato);
	            ps.setInt(2, id);
	            ps.executeUpdate();
	        }
	    }
	 
	 private Ordine creaOrdine(ResultSet rs) throws SQLException {
	        Ordine ordine = new Ordine();
	        ordine.setId(rs.getInt("id"));
	        ordine.setEmail_utente(rs.getString("email_utente"));
	        ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
	        ordine.setTotale_ordine(rs.getFloat("totale_ordine"));
	        ordine.setPan(rs.getInt("pan"));
	        ordine.setCVV(rs.getInt("CVV"));
	        ordine.setScadenza(rs.getString("scadenza"));
	        ordine.setVia(rs.getString("via"));
	        ordine.setCittà(rs.getString("città"));
	        ordine.setCap(rs.getInt("cap"));
	        ordine.setCivico(rs.getString("civico"));
	        ordine.setStato(rs.getString("stato"));
	        return ordine;
	    }

}
