package LMS.wsconsultarlicitacoessipac.controller;


import LMS.wsconsultarlicitacoessipac.services.HttpService;
import LMS.wsconsultarlicitacoessipac.services.LicitacoesSipacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequestMapping(value = "/request-licitacoes")
public class LicitacoesSipacController {

    @Autowired
    private HttpService httpService;

    @Autowired
    private LicitacoesSipacService licitacoesSipacService;

    @RequestMapping(value = "/acesso-teste", method = RequestMethod.GET)
    public void carsList () throws RuntimeException {
        try {
            licitacoesSipacService.persistirDados();
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado. " + e.getMessage());
        }
    }

}
