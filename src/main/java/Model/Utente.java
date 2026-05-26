package Model;

import java.io.Serializable;

public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;
    

    public Utente() {}

    public Utente(String email, String nome, String cognome, String nickname, String password) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.nickname = nickname;
        this.password = password;       
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
    
    
    
    
    private String email;
    private String nome;
    private String cognome;
    private String nickname;
    private String password;
}


