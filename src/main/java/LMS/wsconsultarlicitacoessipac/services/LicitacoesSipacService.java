package LMS.wsconsultarlicitacoessipac.services;

import LMS.wsconsultarlicitacoessipac.dao.LicitacoesSipacDao;
import LMS.wsconsultarlicitacoessipac.dto.LicitacoesSipacDto;
import LMS.wsconsultarlicitacoessipac.entidade.LicitacoesSipac;
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

    public void persistirDados () throws Exception {
        try {
            this.saveAllLicitacoesSipacList(this.converterAllDtosEmEntidades(this.generateLicitacoesSipacDtoFromTable()));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // !! Todo !! -> verificar se as funções comentadas continuam sem utilidade antes do envio.

//    private void saveLicitacoesSipac (LicitacoesSipac licitacoesSipac) {
//        licitacoesSipacDao.save(licitacoesSipac);
//    }

    private void saveAllLicitacoesSipacList (List<LicitacoesSipac> licitacoesSipacList) {
        licitacoesSipacDao.saveAll(licitacoesSipacList);
    }

//    private LicitacoesSipac converterDtoEmEntidade (LicitacoesSipacDto licitacoesSipacDto) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(licitacoesSipacDto, LicitacoesSipac.class);
//    }

    private List<LicitacoesSipac> converterAllDtosEmEntidades (List<LicitacoesSipacDto> licitacoesSipacDtoList) {
        ModelMapper modelMapper = new ModelMapper();
        return licitacoesSipacDtoList.stream().map(a -> modelMapper.map(a, LicitacoesSipac.class)).collect(Collectors.toList());
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
        return licitacoesSipacDto;
    }

    private List<LicitacoesSipacDto> generateLicitacoesSipacDtoFromTable () throws Exception {
        HtmlTable htmlTable = httpService.criaTabela();
        List<LicitacoesSipacDto> licitacoesSipacDtoList = new ArrayList<>();
        try {
            for (int i = 1; i < htmlTable.getRows().size(); i++) {
                List<String> celulas = new ArrayList<>();
                for (final HtmlTableCell cell : htmlTable.getRows().get(i).getCells()) {
                    celulas.add(cell.asNormalizedText());
                }
                licitacoesSipacDtoList.add(this.popularLicitacoesSipac(celulas));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return licitacoesSipacDtoList;
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
