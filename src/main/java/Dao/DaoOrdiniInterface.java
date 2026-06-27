package Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import Model.Ordine;


public interface DaoOrdiniInterface {
	void doSave(Ordine ordine) throws SQLException;
	Ordine doRetrieveByKey(int id) throws SQLException;
	ArrayList<Ordine> doRetrieveByUtente(String email) throws SQLException;
	ArrayList<Ordine> doRetrieveAll() throws SQLException;
	void updateStato(int id, String stato) throws SQLException;
	ArrayList<Ordine> doRetrieveByDateRange(String dataDa, String dataA) throws SQLException;
    int getProssimoProgressivo(String emailUtente) throws SQLException;
}
