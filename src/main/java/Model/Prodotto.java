package Model;

import java.io.Serializable;


public class Prodotto implements Serializable {
    private static final long serialVersionUID = 1L;
    

    public Prodotto() {}

    public Prodotto(int codiceProdotto, String nome, String descrizione, float costo, String immagine, int quantitaMagazzino) {
        this.codiceProdotto = codiceProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.costo = costo;
        this.immagine = immagine;
        this.quantitaMagazzino = quantitaMagazzino;
    }

    // getter e setter
    public int getCodiceProdotto() { return codiceProdotto; }
    public void setCodiceProdotto(int codiceProdotto) { this.codiceProdotto = codiceProdotto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }

    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }

    public int getQuantitaMagazzino() { return quantitaMagazzino; }
    public void setQuantitaMagazzino(int quantitaMagazzino) { this.quantitaMagazzino = quantitaMagazzino; }
    
    private int codiceProdotto;
    private String nome;
    private String descrizione;
    private float costo;
    private String immagine;      // path
    private int quantitaMagazzino;
}