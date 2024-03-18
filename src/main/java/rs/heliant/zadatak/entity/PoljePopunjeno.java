package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "polje_popunjeno")
@Getter
@Setter
@NoArgsConstructor
public class PoljePopunjeno extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_formular_popunjen")
    private FormularPopunjen formularPopunjen;

    @ManyToOne
    @JoinColumn(name = "id_polje")
    private Polje polje;

    @Column(name = "vrednost_tekst")
    private String vrednostTekst;

    @Column(name = "vrednost_broj")
    private Double vrednostBroj;

}
