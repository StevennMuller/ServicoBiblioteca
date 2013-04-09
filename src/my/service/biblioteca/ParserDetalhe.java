package my.service.biblioteca;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;

public class ParserDetalhe {
	private Page page;

	public ParserDetalhe(Page page) {
		this.page = page;
	}

	/*public String extraiLocalizacao() {
		String resultado = page.getWebResponse().getContentAsString();
		String procura = "Estante";
		String localizacao;
		int index, indexfim;
		index = resultado.indexOf(procura);
		localizacao = resultado.substring(index);
		index = localizacao.indexOf("div align");
		localizacao = localizacao.substring(index);
		index = localizacao.indexOf(">");
		indexfim = localizacao.indexOf("</div>");
		localizacao = localizacao.substring(index + 1, indexfim);
		localizacao = localizacao.replaceAll("<br>", " ");
		localizacao = localizacao.replaceAll("</br>", " ");
		System.out.println(localizacao);
		return localizacao;
	}
*/
	public String extraiEditora() {
		String resultado = page.getWebResponse().getContentAsString();
		String procura = "Editora";
		String editora;
		int index, indexfim;
		index = resultado.indexOf(procura);
		editora = resultado.substring(index);
		index = editora.indexOf("div align");
		editora = editora.substring(index);
		index = editora.indexOf(">");
		indexfim = editora.indexOf("</div>");
		editora = editora.substring(index + 1, indexfim);
		editora = editora.replaceAll("<br>", " ");
		editora = editora.replaceAll("</br>", " ");
		System.out.println(editora);
		return editora;
	}

	public String extraiTitulo() {
		String resultado = page.getWebResponse().getContentAsString();
		String procura = "T&iacute;tulo:";
		int index, indexfim;
		String titulo;
		index = resultado.indexOf(procura);
		titulo = resultado.substring(index);
		index = titulo.indexOf(procura);
		indexfim = titulo.indexOf("</div>");
		titulo = titulo.substring(index, indexfim);
		titulo = titulo.replaceAll("<br>", " ");
		titulo = titulo.replaceAll("</br>", " ");
		System.out.println(titulo);
		return titulo;
	}

	public String extraiAutor() {
		String resultado = page.getWebResponse().getContentAsString();
		String procura = "Secundárias";
		String autor;
		int index, indexfim;
		index = resultado.indexOf(procura);
		autor = resultado.substring(index);
		index = autor.indexOf("a href=");
		autor = autor.substring(index);
		index = autor.indexOf(">");
		indexfim = autor.indexOf("</a>");
		autor = autor.substring(index + 1, indexfim);
		autor = autor.replaceAll("<br>", " ");
		autor = autor.replaceAll("</br>", " ");
		System.out.println(autor);
		return autor;
	}

	public String extraiBibliotecaDisponibilidade(Page page) {
		List<String> lista = new ArrayList<String>();
		String Biblioteca,emprestado,localizacao, Disponivel;
		String resultado = page.getWebResponse().getContentAsString();
		String procura = "nowrap><font class=\\'txt\\'>";
		resultado = resultado.replace(procura, "UmaBibliotecaAQUI");
		procura = "UmaBibliotecaAQUI";
		int indexBib, indexDisp, indexFim,indexLocalizacao, indexConsulta,indexEmpr, count;
		count = 0;
		indexConsulta = 0;
		indexBib = resultado.indexOf(procura);
		String ResultadoPorBib;
		while (indexBib >= 0) {
			Biblioteca = resultado.substring(indexBib);
			Biblioteca = Biblioteca.replace(procura, "");
			indexBib = Biblioteca.indexOf("<b>");
			indexFim = Biblioteca.indexOf("</b>");
			ResultadoPorBib = Biblioteca;
			Biblioteca = Biblioteca.substring(indexBib, indexFim);
			Biblioteca = Biblioteca.replaceAll("<b>", "");
			lista.add(Biblioteca);
			//
			
			indexLocalizacao = resultado.indexOf("Localização na estante:");
			localizacao = resultado.substring(indexLocalizacao);
			indexLocalizacao = localizacao.indexOf("<b>");
			indexFim = localizacao.indexOf("</b>");
			localizacao = localizacao.substring(indexLocalizacao, indexFim);
			localizacao=localizacao.replace("<b>","");
			lista.add("Localização: "+localizacao);
			resultado = resultado.replaceFirst("Localização na estante:",
					"UmaLocalizacaoAQUI");
			//
			resultado = resultado.replaceFirst(procura, " ");
			indexDisp = resultado.indexOf("Disponível no Acervo:");
			Disponivel = resultado.substring(indexDisp);
			indexDisp = 0;
			indexFim = Disponivel.indexOf("</b>");
			Disponivel = Disponivel.substring(indexDisp, indexFim);
			lista.add(Disponivel);
			resultado = resultado.replaceFirst("Disponível no Acervo:",
					"UmDisponivelAqui");
			indexEmpr = resultado.indexOf("Emprestado:");
			emprestado = resultado.substring(indexEmpr);
			indexEmpr = 0;
			indexFim = emprestado.indexOf("</b>");
			emprestado = emprestado.substring(indexEmpr, indexFim);
			lista.add(emprestado);
			resultado = resultado.replaceFirst("Emprestado:",
					"UmEmprestadoqui");
			indexBib = resultado.indexOf(procura);

			if (indexBib > -1) {
				ResultadoPorBib = ResultadoPorBib.substring(0, indexBib);
			}
			ResultadoPorBib = ResultadoPorBib.replace("Consulta Local",
					"UmaConsultaAQUI");
			indexConsulta = ResultadoPorBib.indexOf("UmaConsultaAQUI");
			ResultadoPorBib= ResultadoPorBib.replaceFirst("UmaConsultaAQUI", " ");

			while (indexConsulta >= 0) {
				count++;
				indexConsulta = ResultadoPorBib.indexOf("UmaConsultaAQUI");
				ResultadoPorBib = ResultadoPorBib.replaceFirst(
						"UmaConsultaAQUI", " ");
			}

			lista.add("Exemplares de Consulta: " + count);

		}

		return lista.toString();
	}

}
