package Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class Ordine implements Serializable {
	private static final long serialVersionUID = 1L;
	
public Ordine() {}	
	
public Ordine(int id, String email_utente, float totale_ordine, long pan, int CVV, String scadenza, String via,
			String città, int cap, String civico, Timestamp data_ordine, String stato) {
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
		this.data_ordine = data_ordine;
		this.stato = stato;
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

public long getPan() {
	return pan;
}

public void setPan(long pan) {
	this.pan = pan;
}

public int getCVV() {
	return CVV;
}

public void setCVV(int cVV) {
	CVV = cVV;
}

public String getScadenza() {
	return scadenza;
}

public void setScadenza(String scadenza) {
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

public String getCivico() {
	return civico;
}

public void setCivico(String civico) {
	this.civico = civico;
}

public Timestamp getDataOrdine() {
	return data_ordine;
}

public void setDataOrdine(Timestamp data_ordine) {
	this.data_ordine = data_ordine;
}

public String getStato() {
	return stato;
}

public void setStato(String stato) {
	this.stato = stato;
}

public String getDataFormattata() {
    if (data_ordine != null) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        return sdf.format(data_ordine);
    }
    return "";
}

public String getPrezzoFormattato() {
    return String.format("€ %.2f", totale_ordine);
}

public String getIndirizzoCompleto() {
    return via + " " + civico + ", " + cap + " " + città;
}

public String getCartaFormattata() {
    String panStr = String.valueOf(pan);
    if (panStr.length() > 4) {
        return "****" + panStr.substring(panStr.length() - 4);
    }
    return "****";
}




private int id;
private String email_utente;
private float totale_ordine;
private long pan;
private int CVV;
private String scadenza;
private String via;
private String città;
private int cap;
private String civico;
private Timestamp data_ordine;
private String stato;

}
