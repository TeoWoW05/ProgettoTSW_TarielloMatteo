package Model;

public class Prodotto {
    // Costruttori
    public Prodotto() {}
    
    public Prodotto(String codiceProdotto, String nome, String descrizione, 
                    double costo, String immagine, int quantitaMagazzino, String nomeCategoria) {
        this.codiceProdotto = codiceProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.costo = costo;
        this.immagine = immagine;
        this.quantitaMagazzino = quantitaMagazzino;
        this.nomeCategoria = nomeCategoria;
    }
    
    // Getter e Setter
    public String getCodiceProdotto() { 
    	return codiceProdotto; 
    	}
    
    public void setCodiceProdotto(String codiceProdotto) { 
    	this.codiceProdotto = codiceProdotto; 
    	}
    
    public String getNome() { 
    	return nome; 
    	}
    
    public void setNome(String nome) { 
    	this.nome = nome; 
    	}
    
    public String getDescrizione() { 
    	return descrizione; 
    	}
    public void setDescrizione(String descrizione) { 
    	this.descrizione = descrizione; 
    	}
    
    public double getCosto() { 
    	return costo; 
    	}
    
    public void setCosto(double costo) { 
    	this.costo = costo; 
    	}
    
    public String getImmagine() { 
    	return immagine; 
    	}
    
    public void setImmagine(String immagine) { 
    	this.immagine = immagine; 
    	}
    
    public int getQuantitaMagazzino() { 
    	return quantitaMagazzino; 
    	}
    
    public void setQuantitaMagazzino(int quantitaMagazzino) { 
    	this.quantitaMagazzino = quantitaMagazzino; 
    	}
    
    public String getNomeCategoria() { 
    	return nomeCategoria; 
    	}
    
    public void setNomeCategoria(String nomeCategoria) { 
    	this.nomeCategoria = nomeCategoria; 
    	}
    
    
    private String codiceProdotto;
    private String nome;
    private String descrizione;
    private double costo;
    private String immagine;
    private int quantitaMagazzino;
    private String nomeCategoria;
}