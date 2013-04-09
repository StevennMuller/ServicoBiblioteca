package my.service.biblioteca.objetos;

public class Reserva {
	private String livro;
	private String status;

	public Reserva(String livro, String status) {
		super();
		this.livro = livro;
		this.status = status;
	}

	public String getLivro() {
		return livro;
	}

	public void setLivro(String livro) {
		this.livro = livro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
