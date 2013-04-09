package my.service.biblioteca;

import java.util.ArrayList;
import java.util.List;

import my.service.biblioteca.objetos.Emprestimo;
import my.service.biblioteca.objetos.Pendente;
import my.service.biblioteca.objetos.Reserva;

import com.gargoylesoftware.htmlunit.Page;

public class ParserEmprestimo {
	private Page page;

	public ParserEmprestimo(Page page) {
		this.page = page;
	}

	public Emprestimo extraiEmprestimo() {
		ArrayList<Pendente> emprestimos = new ArrayList<Pendente>();
		String result = page.getWebResponse().getContentAsString();
		result = result.replaceFirst("\"txt_azul_11\"  title=", "UmLivroAQUI");
		int a = result.indexOf("UmLivroAQUI");
		String emprestimo, dataDevolve, numRenova;

		while (a > -1) {
			// Livro
			result = result.substring(a);
			a = result.indexOf("UmLivroAQUI");
			int b = result.indexOf("><a");
			emprestimo = result.substring(a, b - 1);
			emprestimo = emprestimo.replace("UmLivroAQUI\"", "");

			// Data
			a = result.indexOf("\"txt_cinza\"");
			result = result.substring(a);
			result = result.replaceFirst("\"txt_cinza\"", "UmDataAqui");
			a = result.indexOf("UmDataAqui");
			b = result.indexOf("</td>");
			dataDevolve = result.substring(a, b);
			dataDevolve = dataDevolve.replaceFirst("UmDataAqui>", "");

			// Renovar
			a = result.indexOf("renovar");
			result = result.substring(a);
			result = result.replaceFirst("renovar", "UmRenovaAqui");
			a = result.indexOf("UmRenovaAqui");
			b = result.indexOf("</td>");
			numRenova = result.substring(a, b);
			numRenova = numRenova.replaceFirst(
					"UmRenovaAqui</strong></a> &nbsp;/ &nbsp;\r\n", "");

			Pendente pes = new Pendente(emprestimo.trim(), dataDevolve.trim(),
					numRenova.trim());

			emprestimos.add(pes);
			a = result.indexOf("\"txt_azul_11\"  title=");
			result = result.replaceFirst("\"txt_azul_11\"  title=",
					"UmLivroAQUI");

		}

		return new Emprestimo(emprestimos, extraiReserva());

	}

	public List<Reserva> extraiReserva() {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		String result = page.getWebResponse().getContentAsString();
		result = result.replaceFirst("\"txt_verde\"", "UmaReservaAQUI");
		int a = result.indexOf("UmaReservaAQUI");
		String reserva, statusReserva;

		while (a > -1) {
			result = result.substring(a);
			a = result.indexOf("UmaReservaAQUI");
			int b = result.indexOf("\">");
			reserva = result.substring(a, b);
			reserva = reserva.replace("UmaReservaAQUI title=\"", "");
			a = result.indexOf("\"txt_cinza\"");
			result = result.substring(a);
			result = result.replaceFirst("\"txt_cinza\"", "UmStatusAqui");
			a = result.indexOf("UmStatusAqui");
			b = result.indexOf("</td>");
			statusReserva = result.substring(a, b);
			statusReserva = statusReserva.replaceFirst(
					"UmStatusAqui>&nbsp;\r\n ", "");

			Reserva res = new Reserva(reserva.trim(), statusReserva.trim());
			reservas.add(res);

			a = result.indexOf("\"txt_verde\"");
			result = result.replaceFirst("\"txt_verde\"", "UmaReservaAQUI");

		}

		return reservas;

	}

}