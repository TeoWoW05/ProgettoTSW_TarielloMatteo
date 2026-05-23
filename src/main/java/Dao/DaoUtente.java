package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import Model.Utente;

public class DaoUtente implements DaoUtenteInterface {
	
	public DaoUtente(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public synchronized void doSave(Utente utente) throws SQLException{
		String insertSQL = "INSERT INTO "+ TABLE_NAME + "(email, nome, cognome, nickname, password) VALUES(?, ?, ?, ?, ?)";
		
		try(Connection connection = ds.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS)){
				
				preparedStatement.setString(1, utente.getEmail());
				preparedStatement.setString(2, utente.getName());
				preparedStatement.setString(3, utente.getCognome());
				preparedStatement.setString(4, utente.getNickname());
				preparedStatement.setString(5, utente.getPass());
				
				preparedStatement.executeUpdate();
				
				try(ResultSet chiaveGenerata = preparedStatement.getGeneratedKeys()){
					if(chiaveGenerata.next()) {
						utente.setEmail(chiaveGenerata.getString(1));
					}
				}
		      }	
	       }
	
	@Override
    public synchronized void doUpdate(Utente utente) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME 
                + " SET nome = ?, cognome = ?, nickname = ?, password = ? WHERE email = ?";
        
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            
            preparedStatement.setString(1, utente.getName());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getNickname());
            preparedStatement.setString(4, utente.getPass());
            preparedStatement.setString(5, utente.getEmail());
            
            preparedStatement.executeUpdate();
        }
    }
	
	@Override
    public synchronized void doDelete(String email) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE email = ?";
        
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        }
    }
	
	 @Override
	    public synchronized void doDelete(Utente utente) throws SQLException {
	        doDelete(utente.getEmail());
	    }
	 
	 @Override
	    public synchronized Utente doRetrieveByKey(String email) throws SQLException {
	        Utente bean = null;
	        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
	            
	            preparedStatement.setString(1, email);
	            
	            try (ResultSet rs = preparedStatement.executeQuery()) {
	                if (rs.next()) {
	                    bean = new Utente();
	                    bean.setEmail(rs.getString("email"));
	                    bean.setName(rs.getString("nome"));
	                    bean.setCognome(rs.getString("cognome"));
	                    bean.setNickname(rs.getString("nickname"));
	                    bean.setPass(rs.getString("password"));
	                }
	            }
	        }
	        return bean;
	    }
	 
	 
	 @Override
	    public synchronized Collection<Utente> doRetrieveAll(String order) throws SQLException {
	        Collection<Utente> utenti = new LinkedList<>();
	        String selectSQL = "SELECT * FROM " + TABLE_NAME;
	        
	        // Whitelist per prevenire SQL injection
	        if (order != null && !order.isEmpty()) {
	            if (order.equals("email") || order.equals("nome") || order.equals("cognome") || 
	                order.equals("nickname") || order.equals("data_registrazione")) {
	                selectSQL += " ORDER BY " + order;
	            } else {
	                selectSQL += " ORDER BY email";
	            }
	        } else {
	            selectSQL += " ORDER BY email";
	        }
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	             ResultSet rs = preparedStatement.executeQuery()) {
	            
	            while (rs.next()) {
	                Utente bean = new Utente();
	                bean.setEmail(rs.getString("email"));
	                bean.setName(rs.getString("nome"));
	                bean.setCognome(rs.getString("cognome"));
	                bean.setNickname(rs.getString("nickname"));
	                bean.setPass(rs.getString("password"));
	                utenti.add(bean);
	            }
	        }
	        return utenti;
	    }
	 
	 @Override
	    public synchronized boolean existsByEmail(String email) throws SQLException {
	        String selectSQL = "SELECT 1 FROM " + TABLE_NAME + " WHERE email = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
	            
	            preparedStatement.setString(1, email);
	            
	            try (ResultSet rs = preparedStatement.executeQuery()) {
	                return rs.next();
	            }
	        }
	    }
	 
	 @Override
	    public synchronized boolean existsByNickname(String nickname) throws SQLException {
	        String selectSQL = "SELECT 1 FROM " + TABLE_NAME + " WHERE nickname = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
	            
	            preparedStatement.setString(1, nickname);
	            
	            try (ResultSet rs = preparedStatement.executeQuery()) {
	                return rs.next();
	            }
	        }
	    }
	
	
	private static final String TABLE_NAME = "Utente";
	private DataSource ds = null;
}
