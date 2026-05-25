package Model;

import java.io.Serializable;

public class Ordine implements Serializable {
	private static final long serialVersionUID = 1L;
	
public Ordine() {}	
	
public Ordine(int id, String email_utente, float totale_ordine, int pan, int CVV, int scadenza, String via,
			String città, int cap, int civico) {
		this.id = id;
		this.email_utente = email_utente;
		this.totale_ordine = totale_ordine;
		this.pan = pan;
		this.CVV = CVV;
		this.scadenza = scadenza;
		this.via = via;
		this.città = città;
		this.cap = cap;
		this.civico = civico;
	}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getEmail_utente() {
	return email_utente;
}

public void setEmail_utente(String email_utente) {
	this.email_utente = email_utente;
}

public float getTotale_ordine() {
	return totale_ordine;
}

public void setTotale_ordine(float totale_ordine) {
	this.totale_ordine = totale_ordine;
}

public int getPan() {
	return pan;
}

public void setPan(int pan) {
	this.pan = pan;
}

public int getCVV() {
	return CVV;
}

public void setCVV(int cVV) {
	CVV = cVV;
}

public int getScadenza() {
	return scadenza;
}

public void setScadenza(int scadenza) {
	this.scadenza = scadenza;
}

public String getVia() {
	return via;
}

public void setVia(String via) {
	this.via = via;
}

public String getCittà() {
	return città;
}

public void setCittà(String città) {
	this.città = città;
}

public int getCap() {
	return cap;
}

public void setCap(int cap) {
	this.cap = cap;
}

public int getCivico() {
	return civico;
}

public void setCivico(int civico) {
	this.civico = civico;
}





private int id;
private String email_utente;
private float totale_ordine;
private int pan;
private int CVV;
private int scadenza;
private String via;
private String città;
private int cap;
private int civico;

}
