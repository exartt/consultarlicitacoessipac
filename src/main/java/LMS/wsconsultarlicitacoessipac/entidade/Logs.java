package LMS.wsconsultarlicitacoessipac.entidade;


import LMS.wsconsultarlicitacoessipac.enums.TipoRequisicao;
import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@Entity
@Table(name = "logs")
@Data
@EqualsAndHashCode(callSuper=false)
@SequenceGenerator(name="postgreslic_id_logs_seq", sequenceName="postgreslic_id_logs_seq", allocationSize = 1)
public class Logs extends AbstractEntity implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "id_logs")
    @GeneratedValue(generator="postgreslic_id_logs_seq",strategy=GenerationType.SEQUENCE)
    private long id;
    @Column(name = "log_descricao")
    private String descricao;
    @Column(name = "log_data")
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "log_tipo_requisicao")
    private TipoRequisicao tipoRequisicao;
}
