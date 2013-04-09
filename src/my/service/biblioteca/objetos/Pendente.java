package my.service.biblioteca.objetos;

public class Pendente {
	private String livro;
	private String dataDev;
	private String numRenova;

	public Pendente(String livro, String dataDev, String numRenova) {
		super();
		this.livro = livro;
		this.dataDev = dataDev;
		this.numRenova = numRenova;
	}

	public String getLivro() {
		return livro;
	}

	public void setLivro(String livro) {
		this.livro = livro;
	}

	public String getDataDev() {
		return dataDev;
	}

	public void setDataDev(String dataDev) {
		this.dataDev = dataDev;
	}

	public String getNumRenova() {
		return numRenova;
	}

	public void setNumRenova(String numRenova) {
		this.numRenova = numRenova;
	}

}
