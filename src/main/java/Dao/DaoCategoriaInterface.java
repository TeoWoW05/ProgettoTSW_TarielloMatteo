package Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.Categoria;

public interface DaoCategoriaInterface {
	void doSave(Categoria categoria) throws SQLException;
	void doDelete(String nome) throws SQLException;
	Categoria doRetrieveByKey(String nome) throws SQLException;
	ArrayList<Categoria> doRetrieveAll() throws SQLException;
	boolean exists(String nome) throws SQLException;
}
