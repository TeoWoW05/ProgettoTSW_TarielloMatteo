package Model;

import java.io.Serializable;

public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;


	public Categoria() {}
	
	public Categoria(String nome) {
		this.nome = nome;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	private String nome;
}
