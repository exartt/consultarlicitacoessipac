package LMS.wsconsultarlicitacoessipac.controller;


import LMS.wsconsultarlicitacoessipac.dto.LicitacaoLidaDto;
import LMS.wsconsultarlicitacoessipac.dto.LicitacoesSipacDto;
import LMS.wsconsultarlicitacoessipac.services.HttpService;
import LMS.wsconsultarlicitacoessipac.services.LicitacoesSipacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping(value = "/consultar-licitacoes")
public class LicitacoesSipacController {

    @Autowired
    private HttpService httpService;

    @Autowired
    private LicitacoesSipacService licitacoesSipacService;

    @RequestMapping(value = "/get-licitacoes-persistir", method = RequestMethod.GET)
    public List<LicitacoesSipacDto> sendLicitacoesPersistindoDados () throws RuntimeException {
        try {
            List<LicitacoesSipacDto> licitacoesSipacDtoList = licitacoesSipacService.generateLicitacoesSipacDtoFromTable();
            licitacoesSipacService.persistirDados(licitacoesSipacDtoList);
            return licitacoesSipacDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/get-licitacoes", method = RequestMethod.GET)
    public List<LicitacoesSipacDto> sendLicitacoes () throws RuntimeException {
        try {
            return licitacoesSipacService.generateLicitacoesSipacDtoFromTable();
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/mark-all", method = RequestMethod.GET)
    public void markAllAsRead () {

    }

    @RequestMapping(value = "/one-is-readed", method = RequestMethod.GET)
    public void markASReadedOrUnreaded (@RequestBody LicitacaoLidaDto licitacaoLidaDto) {

    }

}
