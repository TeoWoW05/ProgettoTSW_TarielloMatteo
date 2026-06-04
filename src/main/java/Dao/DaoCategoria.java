package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import Model.Categoria;

public class DaoCategoria implements DaoCategoriaInterface {

	private DataSource ds;
	private static final String TABLE_NAME = "Categoria";
	
	public DaoCategoria(DataSource ds) {
		this.ds = ds;
	}
	
	 @Override
	    public synchronized void doSave(Categoria categoria) throws SQLException {
	        String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome) VALUES (?)";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
	            
	            preparedStatement.setString(1, categoria.getNome());
	            preparedStatement.executeUpdate();
	        }
	    }
	 
	 @Override
	    public synchronized void doDelete(String nome) throws SQLException {
	        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nome = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
	            
	            preparedStatement.setString(1, nome);
	            preparedStatement.executeUpdate();
	        }
	 }
	        @Override
	        public synchronized Categoria doRetrieveByKey(String nome) throws SQLException {
	            Categoria categoria = null;
	            String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
	            
	            try (Connection connection = ds.getConnection();
	                 PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
	                
	                preparedStatement.setString(1, nome);
	                
	                try (ResultSet rs = preparedStatement.executeQuery()) {
	                    if (rs.next()) {
	                        categoria = new Categoria();
	                        categoria.setNome(rs.getString("nome"));
	                    }
	                }
	            }
	            return categoria;
	        }
	        
	        @Override
	        public synchronized ArrayList<Categoria> doRetrieveAll() throws SQLException {
	            ArrayList<Categoria> categorie = new ArrayList<>();
	            String selectSQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY nome";
	            
	            try (Connection connection = ds.getConnection();
	                 PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	                 ResultSet rs = preparedStatement.executeQuery()) {
	                
	                while (rs.next()) {
	                    Categoria categoria = new Categoria();
	                    categoria.setNome(rs.getString("nome"));
	                    categorie.add(categoria);
	                }
	            }
	            return categorie;
	        }
	        
	        @Override
	        public synchronized boolean exists(String nome) throws SQLException {
	            String selectSQL = "SELECT 1 FROM " + TABLE_NAME + " WHERE nome = ?";
	            
	            try (Connection connection = ds.getConnection();
	                 PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
	                
	                preparedStatement.setString(1, nome);
	                
	                try (ResultSet rs = preparedStatement.executeQuery()) {
	                    return rs.next();
	                }
	            }
	    }
}
