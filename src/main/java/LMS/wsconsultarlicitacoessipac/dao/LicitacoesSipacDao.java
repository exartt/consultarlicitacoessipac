package LMS.wsconsultarlicitacoessipac.dao;

import LMS.wsconsultarlicitacoessipac.entidade.LicitacoesSipac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LicitacoesSipacDao extends JpaRepository<LicitacoesSipac, Long> {
    List<LicitacoesSipac> findByDataConsulta (LocalDate dataConsulta);
    LicitacoesSipac findTopByCodigoLicitacao (String codigoLicitacao);
    Boolean existsByDataConsulta (LocalDate dataConsulta);
    Boolean existsByCodigoLicitacao (String codigoLicitacao);

    Boolean existsById (long id);
}
