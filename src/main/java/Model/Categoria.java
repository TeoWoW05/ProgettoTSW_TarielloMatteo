package Model;

public class Categoria {
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
