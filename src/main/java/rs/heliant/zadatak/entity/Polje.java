package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.heliant.zadatak.enums.TipPolja;

import java.util.List;

@Entity
@Table(name = "polje")
@Getter
@Setter
@NoArgsConstructor
public class Polje extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_formular")
    private Formular formular;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "prikazni_redosled")
    private Integer prikazniRedosled;

    @Column(name = "tip", columnDefinition = "varchar(5)")
    @Enumerated(EnumType.STRING)
    private TipPolja tip;

    @OneToMany(mappedBy = "polje", cascade = CascadeType.REMOVE)
    private List<PoljePopunjeno> popunjenaPolja;

}
