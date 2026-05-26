package MetodoDiHashing;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashing {
    
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Crea l'hash della password con salt incorporato
     * Restituisce: salt:hash (entrambi in Base64)
     */
    public static String hashPassword(String password) {
        try {
            // Genera salt casuale
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            // Crea hash SHA-256 con salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hash = md.digest(password.getBytes());
            
            // Restituisce salt e hash concatenati
            return Base64.getEncoder().encodeToString(salt) + ":" + 
                   Base64.getEncoder().encodeToString(hash);
                   
        } catch (Exception e) {
            throw new RuntimeException("Errore hashing", e);
        }
    }
    
    /**
     * Verifica password
     */
    public static boolean checkPassword(String password, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hash = md.digest(password.getBytes());
            
            return Base64.getEncoder().encodeToString(hash).equals(parts[1]);
            
        } catch (Exception e) {
            return false;
        }
    }
}