package my.service.biblioteca;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import my.service.biblioteca.objetos.Emprestimo;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
@Path("/emprestimo")
public class ServicoEmprestado {
	
	@GET
	@Path("/{matricula}/{senha}")
	@Produces({ MediaType.APPLICATION_JSON })
	
	public Emprestimo getEmprestimo (@PathParam("matricula") String matricula,@PathParam("senha") String senha)
	{
		//Instancia um browser cliente
		final WebClient webClient = new WebClient();
		CookieManager cookieMan = new CookieManager();
		cookieMan = webClient.getCookieManager();
		cookieMan.setCookiesEnabled(true);
		Cookie cookie;
		
		
		String link="http://www.pergamum.bib.ufba.br/pergamum/biblioteca/index.php?rs=ajax_valida_acesso&rsargs[]="+matricula+"&rsargs[]="+senha ;  
		//loga na página
		try {
		
			webClient.getPage("http://www.pergamum.bib.ufba.br/");
			HtmlPage page1=webClient.getPage(link);
			System.out.println(page1.getWebResponse().getContentAsString());
			if (page1.getWebResponse().getContentAsString().indexOf("Inválida")>=0)
			{
				Emprestimo emp=new Emprestimo();
				emp.setValido(false);
				return emp;
			}
			
			
			/*cookie=(Cookie)cookieMan.getCookies().toArray()[0];
			cookieMan.clearCookies();
			cookieMan.addCookie(cookie);
			webClient.setCookieManager(cookieMan);*/
			
			HtmlPage page=webClient.getPage("http://www.pergamum.bib.ufba.br/pergamum/biblioteca_s/meu_pergamum/emp_pendente.php");
			//return (page.getWebResponse().getContentAsString());
			ParserEmprestimo s = new ParserEmprestimo(page);
			return s.extraiEmprestimo();
			
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
