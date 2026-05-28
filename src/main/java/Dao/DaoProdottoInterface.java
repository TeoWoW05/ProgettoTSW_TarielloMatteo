package Dao;

import java.sql.SQLException;
import java.util.List;
import Model.Prodotto;

public interface DaoProdottoInterface {
	
	void doSave(Prodotto prodotto) throws SQLException;
	
	void doUpdate(Prodotto prodotto) throws SQLException;
	
	void doDelete(int codiceProdotto) throws SQLException;
	
	Prodotto doRetrieveByKey(int codiceProdotto) throws SQLException;
	
	List<Prodotto> doRetrieveAll() throws SQLException;
	
	List<Prodotto> doRetrieveByCategoria(String categoria) throws SQLException;
	
	List<Prodotto> doRetrieveByNome(String nome) throws SQLException;
	
	List<Prodotto> doRetrieveByPrezzo(float min,float max) throws SQLException;
	
	void updateQuantita(int codiceProdotto,int quantita) throws SQLException;
	
}
