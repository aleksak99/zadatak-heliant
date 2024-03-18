package rs.heliant.zadatak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "statistika")
@Getter
@Setter
@NoArgsConstructor
public class Statistika extends BaseEntity {

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "broj_popunjenih_formulara")
    private Integer brojPopunjenihFormulara;

}
