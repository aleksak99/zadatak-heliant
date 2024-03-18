package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.heliant.zadatak.enums.TipPolja;

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

    @Column(name = "tip")
    @Enumerated(EnumType.STRING)
    private TipPolja tip;

}