package Dao;

import java.sql.SQLException;
import java.util.Collection;
import Model.Utente;

public interface DaoUtenteInterface {
    
    /** Operazione di salvataggio (CREATE) */
    public void doSave(Utente utente) throws SQLException;
    
    /** Operazione di aggiornamento (UPDATE) */
    public void doUpdate(Utente utente) throws SQLException;
    
    /** Operazione di cancellazione (DELETE) per chiave */
    public void doDelete(String email) throws SQLException;
    
    /** Operazione di cancellazione (DELETE) per oggetto */
    public void doDelete(Utente utente) throws SQLException;
    
    /** Operazione di retrieve per chiave primaria */
    public Utente doRetrieveByKey(String email) throws SQLException;
    
    /** Operazione di retrieve con ordinamento (READ all) */
    public Collection<Utente> doRetrieveAll(String order) throws SQLException;
    
    /** Verifica esistenza nickname */
    public boolean existsByNickname(String nickname) throws SQLException;
    
    /** Verifica esistenza email */
    public boolean existsByEmail(String email) throws SQLException;
}