package Model;

import java.io.Serializable;

public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;
    

    public Utente() {
    	this.ruolo = "user";
    }


    
    public String getEmail() { return email; }
    
    public void setEmail(String email) { this.email = email; }

    public String getName() { return nome; }
    
    public void setName(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getNickname() { return nickname; }
    
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPass() { return password; }
    
    public void setPass(String password) { this.password = password; }
    
    public String getRuolo() { return ruolo; }
    
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    
    public boolean isAdmin() {
        return "admin".equals(this.ruolo);
    }
    
    public boolean isUser() {
        return "user".equals(this.ruolo);
    }
    
    
    private String email;
    private String nome;
    private String cognome;
    private String nickname;
    private String password;
    private String ruolo;
}


