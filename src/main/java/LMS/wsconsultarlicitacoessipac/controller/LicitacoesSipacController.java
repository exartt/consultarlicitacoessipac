package LMS.wsconsultarlicitacoessipac.controller;


import LMS.wsconsultarlicitacoessipac.dto.LicitacaoLidaDto;
import LMS.wsconsultarlicitacoessipac.dto.LicitacoesSipacDto;
import LMS.wsconsultarlicitacoessipac.entidade.LicitacoesSipac;
import LMS.wsconsultarlicitacoessipac.enums.TipoRequisicao;
import LMS.wsconsultarlicitacoessipac.services.LicitacoesSipacService;
import LMS.wsconsultarlicitacoessipac.services.LogsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping(value = "/licitacoes")
public class LicitacoesSipacController {

    @Autowired
    private LicitacoesSipacService licitacoesSipacService;

    @Autowired
    private LogsService logsService;

    @RequestMapping(value = "/get-licitacoes-persistir", method = RequestMethod.GET)
    public List<LicitacoesSipacDto> sendLicitacoesPersistindoDados () throws Exception {
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<LicitacoesSipac> licitacoesSipacList = licitacoesSipacService.persistirDados(licitacoesSipacService.generateLicitacoesSipacDtoFromTable());
            logsService.registrarRequest(TipoRequisicao.ListaDeLicitacoesComPersistencia, "Requisição de listagem com persistência de dados.");
            return licitacoesSipacList.stream().map(a -> modelMapper.map(a, LicitacoesSipacDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.ListaDeLicitacoesComPersistencia, "ERROR! Ocorreu um erro na listagem com persistência." + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado na tentativa listar as licitações atuais. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/get-licitacoes", method = RequestMethod.GET)
    public List<LicitacoesSipacDto> sendLicitacoes () throws Exception {
        try {
            logsService.registrarRequest(TipoRequisicao.ListaDeLicitacoes, "Requisição da listagem de licitações.");
            return licitacoesSipacService.generateLicitacoesSipacDtoFromTable();
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.ListaDeLicitacoesComPersistencia, "ERROR! Ocorreu um erro na listagem de licitações." + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado na tentativa listar as licitações atuais. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/mark-all", method = RequestMethod.GET)
    public String markAllAsRead (boolean markAll) throws Exception {
        try {
            licitacoesSipacService.markAllAsLidoOuNaoLido(markAll);
            logsService.registrarRequest(TipoRequisicao.MarkAll, "Todas as licitações do dia foram marcadas como " + markAll + ".");
            return "Operação efetuada com sucesso!";
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.MarkAll, "ERROR! não foi possível marcar todas as licitações como " + markAll + ". " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado durante a marcação das licitações. " + e.getMessage());
        }
    }
    @RequestMapping(value = "/mark-all/{markAll}", method = RequestMethod.PATCH)
    public String markAllAsReadPatch (@PathVariable("markAll") boolean markAll) throws Exception {
        try {
            licitacoesSipacService.markAllAsLidoOuNaoLido(markAll);
            logsService.registrarRequest(TipoRequisicao.MarkAll, "Todas as licitações do dia foram marcadas como " + markAll + ".");
            return "Operação efetuada com sucesso!";
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.MarkAll, "ERROR! não foi possível marcar todas as licitações como " + markAll + ". " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado durante a marcação das licitações. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/mark-one", method = RequestMethod.POST)
    public LicitacoesSipacDto markASReadedOrUnreaded (@RequestBody LicitacaoLidaDto licitacaoLidaDto) throws Exception {
        try {
            ModelMapper modelMapper = new ModelMapper();
            LicitacoesSipac licitacoesSipac = licitacoesSipacService.lidoOuNaoLido(licitacaoLidaDto);
            logsService.registrarRequest(TipoRequisicao.MarkOne, "A licitação " + licitacaoLidaDto.getCodigoLicitacao() + " foi marcada como " + licitacaoLidaDto.isReaded() + ".");
            return modelMapper.map(licitacoesSipac, LicitacoesSipacDto.class);
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.MarkOne, "ERROR! Não foi possível marcar a licitação " + licitacaoLidaDto.getCodigoLicitacao() + " como " + licitacaoLidaDto.isReaded() + ". " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado durante a marcação das licitações. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/mark-one/{id}", method = RequestMethod.PATCH)
    public LicitacoesSipacDto markASReadedOrUnreadedPatch (@PathVariable("id") long id, boolean readed) throws Exception {
        try {
            LicitacaoLidaDto licitacaoLidaDto = new LicitacaoLidaDto(id, readed, null);
            ModelMapper modelMapper = new ModelMapper();
            LicitacoesSipac licitacoesSipac = licitacoesSipacService.lidoOuNaoLidoPorId(licitacaoLidaDto);
            logsService.registrarRequest(TipoRequisicao.MarkOne, "A licitação de id " + licitacaoLidaDto.getId() + " foi marcada como " + licitacaoLidaDto.isReaded() + ".");
            return modelMapper.map(licitacoesSipac, LicitacoesSipacDto.class);
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.MarkOne, "ERROR! Não foi possível marcar a licitação de id " + id + " como " + readed + ". " + e.getMessage());
            throw new RuntimeException("Ocorreu um erro inesperado durante a marcação das licitações. " + e.getMessage());
        }
    }
    @RequestMapping(value = "/mark-many", method = RequestMethod.POST)
    public String markManyAsReadedOrUnreaded (@RequestBody List<LicitacaoLidaDto> licitacoesToMark) throws Exception {
        try {
            licitacoesSipacService.manyAsLidoOuNaoLido(licitacoesToMark);
            logsService.registrarRequest(TipoRequisicao.MarkMany, "As licitações selecionadas foram marcadas com sucesso.");
            return "Operação efetuada com sucesso!";
        } catch (Exception e) {
            logsService.registrarRequest(TipoRequisicao.MarkMany, "ERROR! Não foi possivel marcar as licitações selecionadas.");
            throw new RuntimeException("Ocorreu um erro inesperado durante a marcação das licitações. " + e.getMessage());
        }
    }

}
