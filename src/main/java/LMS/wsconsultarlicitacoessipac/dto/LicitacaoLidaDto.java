package LMS.wsconsultarlicitacoessipac.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LicitacaoLidaDto {
    private long id;
    private boolean readed;
    private String codigoLicitacao;
}
