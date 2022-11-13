package LMS.wsconsultarlicitacoessipac.entidade;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "postgreslic_licitacoes")
@Data
@EqualsAndHashCode(callSuper=false)
@SequenceGenerator(name="postgreslic_id_licitacoes_sipac_seq", sequenceName="postgreslic_id_licitacoes_sipac_seq", allocationSize = 1)
public class LicitacoesSipac extends AbstractEntity implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "id_licitacoes_sipac")
    @GeneratedValue(generator="postgreslic_id_licitacoes_sipac_seq",strategy=GenerationType.SEQUENCE)
    private long id;
    @Column(name = "lic_codigo_licitacao")
    private String codigoLicitacao;
    @Column(name = "lic_processo")
    private String processo;
    @Column(name = "lic_status")
    private String status;
    @Column(name = "lic_localizacao")
    private String localizacao;
    @Column(name = "lic_vigencia_inicio")
    private LocalDate dataVigenciaInicial;
    @Column(name = "lic_vigencia_fim")
    private LocalDate dataVigenciaFinal;
    @Column(name = "lic_url_visualizar")
    private String urlVisualizar;
    @Column(name = "lic_url_itens_ata")
    private String urlItensAta;
    @Column(name = "lic_url_download")
    private String urlDownload;
    @Column(name = "lic_registro_lido")
    private boolean registroLido;
    @Column(name = "lic_url_processos")
    private String urlProcessos;
    @Column(name = "lic_descricao")
    private String descricao;
    @Column(name = "lic_data_consulta")
    private LocalDate dataConsulta;

}
