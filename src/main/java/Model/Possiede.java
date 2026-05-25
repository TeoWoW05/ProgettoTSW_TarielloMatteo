package Model;

import java.io.Serializable;

public class Possiede implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Possiede(int codiceProdotto, String nomeCategoria) {
      	this.codiceProdotto = codiceProdotto;
		this.nomeCategoria = nomeCategoria;
	}
	
	
	public int getCodiceProdotto() {
		return codiceProdotto;
	}
	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	private int codiceProdotto;
	private String nomeCategoria;
}
