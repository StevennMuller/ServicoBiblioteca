package my.service.biblioteca.parsers;

import java.util.ArrayList;
import java.util.List;

import my.service.biblioteca.objetos.Disponibilidade;

import com.gargoylesoftware.htmlunit.Page;

public class ParserDetalhe {
	Page page;

	public ParserDetalhe(Page page) {
		this.page = page;
	}

	

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
		editora = editora.replaceAll("</br>", " ").replaceAll("&nbsp;", "");
		
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
		titulo = titulo.replaceAll("</br>", " ").replaceAll("&nbsp;", "").replaceAll("T&iacute;tulo:", "");
		
		return titulo;
	}


	public List<Disponibilidade> extraiBibliotecaDisponibilidade(Page page) {
		
		List<Disponibilidade> listaDisponibilidade = new ArrayList<Disponibilidade>();
		
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
			
			//Objeto para ser inserido na lista de disponibilidade para ser retornado.
			Disponibilidade disponibilidade = new Disponibilidade();

			// Nome da biblioteca
			Biblioteca = resultado.substring(indexBib);
			Biblioteca = Biblioteca.replace(procura, "");
			indexBib = Biblioteca.indexOf("<b>");
			indexFim = Biblioteca.indexOf("</b>");
			ResultadoPorBib = Biblioteca;
			Biblioteca = Biblioteca.substring(indexBib, indexFim);
			Biblioteca = Biblioteca.replaceAll("<b>", "").replaceAll("&nbsp;", "");
			disponibilidade.setNomeBiblioteca(Biblioteca);

			//Localiza��o na estante da biblioteca
			indexLocalizacao = resultado.indexOf("Localiza��o na estante:");
			localizacao = resultado.substring(indexLocalizacao);
			indexLocalizacao = localizacao.indexOf("<b>");
			indexFim = localizacao.indexOf("</b>");
			localizacao = localizacao.substring(indexLocalizacao, indexFim);
			localizacao=localizacao.replace("<b>","").replaceAll("&nbsp;", "");;
			disponibilidade.setLocalizacaoEstante(localizacao);
			
			//Marcar a primiera localiza��o. Pode haver mais de uma localiza��o.
			// Na pr�xima itera��o do while, ele n�o pega novamente a mesma localiza��o.
			resultado = resultado.replaceFirst("Localiza��o na estante:",
					"UmaLocalizacaoAQUI");
			
			//Quantidade dispon�vel no acervo
			resultado = resultado.replaceFirst(procura, " ");
			indexDisp = resultado.indexOf("Dispon�vel no Acervo:");
			Disponivel = resultado.substring(indexDisp);
			indexDisp = 0;
			indexFim = Disponivel.indexOf("</b>");
			Disponivel = Disponivel.substring(indexDisp, indexFim);
			int qdtDisponivel = Integer.parseInt((Disponivel.replace("Dispon�vel no Acervo:", "")).trim());
			disponibilidade.setQtdDisponivel(qdtDisponivel);
			
			// Na pr�xima itera��o do while, ele n�o pega novamente. Apenas para controle.
			resultado = resultado.replaceFirst("Dispon�vel no Acervo:",
					"UmDisponivelAqui");
			
			//Quantidade emprestado
			indexEmpr = resultado.indexOf("Emprestado:");
			emprestado = resultado.substring(indexEmpr);
			indexEmpr = 0;
			indexFim = emprestado.indexOf("</b>");
			emprestado = emprestado.substring(indexEmpr, indexFim);
			int qtdEmprestado = Integer.parseInt(emprestado.replace("Emprestado:", "").trim());
			disponibilidade.setQtdEsprestado(qtdEmprestado);
			
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

			disponibilidade.setQtdExemplarConsulta(count);
			
			//Adiciona na lista a ser retornada
			listaDisponibilidade.add(disponibilidade);
		}

		return listaDisponibilidade;
	}

}
