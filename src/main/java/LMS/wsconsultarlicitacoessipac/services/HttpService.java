package LMS.wsconsultarlicitacoessipac.services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpService {
    private final String httpsUrl = "https://sig.ifsc.edu.br/public/listaEditais.do?tipo=2&aba=p-editais-atas&buscaTodas=true&acao=544";

    public HtmlElement connectSipac () throws Exception {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            final HtmlPage page = client.getPage(httpsUrl);
            return (HtmlElement) page.getByXPath("/html/body/div/div/div[2]/table").get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    DomElement domElement = page.querySelector("//*[@id=\"corpo\"]/table/tbody/tr[1]/td[7]/a[1]");
    public HtmlTable criaTabela () throws Exception {
        try {
            HtmlElement pageByXPath = this.connectSipac();
            return (HtmlTable) pageByXPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        final String caption = htmlTable.getCaptionText();
        return null;
    }

}