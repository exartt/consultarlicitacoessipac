package LMS.wsconsultarlicitacoessipac.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LicitacoesSipacDto {
    private Long id;
    private String codigoLicitacao;
    private String processo;
    private String status;
    private String localizacao;
    private LocalDate dataVigenciaInicial;
    private LocalDate dataVigenciaFinal;
    private String descricao;
}
