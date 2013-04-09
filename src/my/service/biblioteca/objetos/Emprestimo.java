package my.service.biblioteca.objetos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Emprestimo {
	public Emprestimo() {
		super();
		// TODO Auto-generated constructor stub
	}

	private List<Pendente> emprestimos;
	private List<Reserva> reservas;
	private boolean Valido=true;
	

	public boolean isValido() {
		return Valido;
	}

	public void setValido(boolean valido) {
		Valido = valido;
	}

	public Emprestimo(List<Pendente> emprestimos, List<Reserva> reservas) {
		super();
		this.emprestimos = emprestimos;
		this.reservas = reservas;
	}

	public List<Pendente> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Pendente> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
}
