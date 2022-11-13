package LMS.wsconsultarlicitacoessipac.services;


import LMS.wsconsultarlicitacoessipac.dao.LogsDao;
import LMS.wsconsultarlicitacoessipac.entidade.Logs;
import LMS.wsconsultarlicitacoessipac.enums.TipoRequisicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class LogsService {

    @Autowired
    private LogsDao logsDao;

    public void registrarRequest (TipoRequisicao tipoRequisicao, String descricao) throws Exception {
        Logs logs = new Logs();
        logs.setTipoRequisicao(tipoRequisicao);
        logs.setData(LocalDateTime.now());
        logs.setDescricao(descricao);
        this.save(logs);
    }

    private void save (Logs logs) throws Exception {
        try {
            logsDao.save(logs);
        } catch (Exception e) {
            throw new Exception("Erro ao tentar salvar log. ", e.getCause());
        }
    }
}
