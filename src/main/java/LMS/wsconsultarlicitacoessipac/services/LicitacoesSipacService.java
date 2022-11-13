package LMS.wsconsultarlicitacoessipac.services;

import LMS.wsconsultarlicitacoessipac.dao.LicitacoesSipacDao;
import LMS.wsconsultarlicitacoessipac.dto.LicitacaoLidaDto;
import LMS.wsconsultarlicitacoessipac.dto.LicitacoesSipacDto;
import LMS.wsconsultarlicitacoessipac.entidade.LicitacoesSipac;
import LMS.wsconsultarlicitacoessipac.entidade.Logs;
import LMS.wsconsultarlicitacoessipac.enums.TipoRequisicao;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class LicitacoesSipacService {

    @Autowired
    private LicitacoesSipacDao licitacoesSipacDao;
    @Autowired
    private HttpService httpService;

    public List<LicitacoesSipac> getAllByDataConsulta (LocalDate data) throws Exception {
        try {
            return licitacoesSipacDao.findByDataConsulta(data);
        } catch (Exception e) {
            throw new Exception("Não foi possível carregar a listagem de licitações do dia " + data+ ".");
        }
    }
    public LicitacoesSipac getByCodigoLicitacao (String codigoLicitacao) throws Exception {
        if (codigoLicitacao == null) {
            throw  new Exception("Por favor, forneca um código de licitação válido.");
        }
        try {
            return licitacoesSipacDao.findTopByCodigoLicitacao(codigoLicitacao);
        } catch (Exception e) {
            throw new Exception("Não foi encontrado a licitação com o código " + codigoLicitacao + ". Por favor verifique se o código fornecido é válido e tente novamente.");
        }
    }
    public void markAllAsLidoOuNaoLido (boolean markAll) throws Exception {
        List<LicitacoesSipac> licitacoesSipacList = this.getAllByDataConsulta(LocalDate.now());
        if (licitacoesSipacList == null) {
            licitacoesSipacList = this.persistirDados(this.generateLicitacoesSipacDtoFromTable());
        }
        for(LicitacoesSipac licitacoesSipac : licitacoesSipacList) {
            licitacoesSipac.setRegistroLido(markAll);
        }
    }
    public void manyAsLidoOuNaoLido (List<LicitacaoLidaDto> licitacaoLidaDtoList) throws Exception {
        for (LicitacaoLidaDto licitacaoLidaDto : licitacaoLidaDtoList) {
            this.lidoOuNaoLido(licitacaoLidaDto);
        }
    }
    private LicitacoesSipac getById (long id) throws Exception {
        return licitacoesSipacDao.findById(id).orElseThrow(() -> new Exception("Não existe um registro com o id " + id));
    }
    public void lidoOuNaoLido (LicitacaoLidaDto licitacaoLidaDto) throws Exception {
        if (!licitacoesSipacDao.existsByCodigoLicitacao(licitacaoLidaDto.getCodigoLicitacao())) {
            this.persistirDados(this.generateLicitacoesSipacDtoFromTable());
        }
        LicitacoesSipac licitacoesSipac = this.getByCodigoLicitacao(licitacaoLidaDto.getCodigoLicitacao());
        licitacoesSipac.setRegistroLido(licitacaoLidaDto.isReaded());
    }
    public void lidoOuNaoLidoPorId (LicitacaoLidaDto licitacaoLidaDto) throws Exception {
        if (licitacoesSipacDao.existsById(licitacaoLidaDto.getId())) {
            LicitacoesSipac licitacoesSipac = this.getById(licitacaoLidaDto.getId());
            licitacoesSipac.setRegistroLido(licitacaoLidaDto.isReaded());
        } else {
            throw new Exception("Não existe registro com o id: " + licitacaoLidaDto.getId());
        }
    }
    public List<LicitacoesSipac> persistirDados(List<LicitacoesSipacDto> licitacoesSipacDtoList) throws Exception {
        try {
            if (isDadosPersistidos(LocalDate.now())) {
                return this.atualizaDados(licitacoesSipacDtoList);
            } else {
                return this.saveAllLicitacoesSipacList(this.converterAllDtosEmEntidades(licitacoesSipacDtoList));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public List<LicitacoesSipacDto> generateLicitacoesSipacDtoFromTable () throws Exception {
        HtmlElement pageSipac = httpService.connectSipac();
        HtmlTable htmlTable = criaTabela(pageSipac);
        List<LicitacoesSipacDto> licitacoesSipacDtoList = new ArrayList<>();
        try {
            for (int rowAtual = 1; rowAtual < htmlTable.getRows().size(); rowAtual++) {
                List<String> celulas = new ArrayList<>();
                for (final HtmlTableCell cell : htmlTable.getRows().get(rowAtual).getCells()) {
                    celulas.add(cell.asNormalizedText());
                }
                celulas.remove(celulas.size() - 1);
                this.getUrlsNaLinhaDaTabela(celulas, pageSipac, rowAtual);
                licitacoesSipacDtoList.add(this.popularLicitacoesSipac(celulas));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return licitacoesSipacDtoList;
    }
    private Boolean isDadosPersistidos (LocalDate data) {
        return licitacoesSipacDao.existsByDataConsulta(data);
    }
    private List<LicitacoesSipac> saveAllLicitacoesSipacList (List<LicitacoesSipac> licitacoesSipacList) {
        return licitacoesSipacDao.saveAll(licitacoesSipacList);
    }
    private List<LicitacoesSipac> converterAllDtosEmEntidades (List<LicitacoesSipacDto> licitacoesSipacDtoList) {
        ModelMapper modelMapper = new ModelMapper();
        return licitacoesSipacDtoList.stream().map(a -> modelMapper.map(a, LicitacoesSipac.class)).collect(Collectors.toList());
    }


    private List<LicitacoesSipac> atualizaDados (List<LicitacoesSipacDto> licitacoesSipacDtoList) throws Exception {
        List<LicitacoesSipac> licitacoesSipacList = new ArrayList<>();
        for (LicitacoesSipacDto licitacoesSipacDto : licitacoesSipacDtoList) {
            LicitacoesSipac licitacoesSipac = this.getByCodigoLicitacao(licitacoesSipacDto.getCodigoLicitacao());
            if (licitacoesSipac == null) {
                licitacoesSipac = new LicitacoesSipac();
            }
            licitacoesSipac.setProcesso(licitacoesSipac.getProcesso());
            licitacoesSipac.setStatus(licitacoesSipac.getStatus());
            licitacoesSipac.setLocalizacao(licitacoesSipac.getLocalizacao());
            licitacoesSipac.setDataVigenciaInicial(licitacoesSipac.getDataVigenciaInicial());
            licitacoesSipac.setDataVigenciaFinal(licitacoesSipac.getDataVigenciaFinal());
            licitacoesSipac.setUrlVisualizar(licitacoesSipac.getUrlVisualizar());
            licitacoesSipac.setUrlItensAta(licitacoesSipac.getUrlItensAta());
            licitacoesSipac.setUrlDownload(licitacoesSipac.getUrlDownload());
            licitacoesSipac.setUrlProcessos(licitacoesSipac.getUrlProcessos());
            licitacoesSipac.setDescricao(licitacoesSipac.getDescricao());
            licitacoesSipacList.add(licitacoesSipacDao.save(licitacoesSipac));
        }
        return licitacoesSipacList;
    }
    private LicitacoesSipacDto popularLicitacoesSipac (List<String> celulas) {
        LicitacoesSipacDto licitacoesSipacDto = new LicitacoesSipacDto();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        licitacoesSipacDto.setCodigoLicitacao(celulas.get(atomicInteger.get()));
        licitacoesSipacDto.setProcesso(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setStatus(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setLocalizacao(celulas.get(atomicInteger.incrementAndGet()));
        this.setDatas(licitacoesSipacDto, celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setDescricao(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setUrlProcessos(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setUrlVisualizar(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setUrlItensAta(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setUrlDownload(celulas.get(atomicInteger.incrementAndGet()));
        licitacoesSipacDto.setDataConsulta(LocalDate.now());
        return licitacoesSipacDto;
    }
    private HtmlTable criaTabela (HtmlElement pageSipac) {
        HtmlElement pageByXPath = pageSipac;
        return (HtmlTable) pageByXPath;
    }

    private void getUrlsNaLinhaDaTabela (List<String> celulas, HtmlElement pageSipac, int rowAtual) {
        String urlSipac = "https://sig.ifsc.edu.br";
        String[] urlTails = {"a", "a[1]", "a[2]", "a[3]"};
        for(String tail : urlTails) {
            HtmlAnchor htmlUrl = pageSipac.getFirstByXPath("/html/body/div/div/div[2]/table/tbody/tr[" + rowAtual + "]/td[7]/" + tail);
            String urlToUse = urlSipac.concat(htmlUrl.getHrefAttribute());
            celulas.add(urlToUse);
        }
    }
    private void setDatas (LicitacoesSipacDto licitacoesSipacDto, String dataRange) {
        String[] datas = dataRange.split(" - ");
        licitacoesSipacDto.setDataVigenciaInicial(this.converteDataStringToDataLocalDate(datas[0]));
        licitacoesSipacDto.setDataVigenciaFinal(this.converteDataStringToDataLocalDate(datas[1]));
    }
    private LocalDate converteDataStringToDataLocalDate (String data) {
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
