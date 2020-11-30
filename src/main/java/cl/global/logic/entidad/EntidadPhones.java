package cl.global.logic.entidad;

import cl.global.logic.config.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "phones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EntidadPhones extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "phones_id_seq")
    @SequenceGenerator(name = "phones_id_seq", sequenceName = "phones_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "contry_code")
    private String contryCode;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private EntidadUsuarios entidadUsuarios;


}
