package listener;

import jakarta.servlet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        
        try {
            // Ottieni il DataSource via JNDI
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/ProgettoTSW");
            
            // Salva il DataSource come attributo del contesto
            context.setAttribute("DataSource", ds);
            
            System.out.println("✅ DataSource inizializzato correttamente!");
            
        } catch (NamingException e) {
            System.err.println("❌ Errore durante l'inizializzazione del DataSource!");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Applicazione arrestata");
    }
}