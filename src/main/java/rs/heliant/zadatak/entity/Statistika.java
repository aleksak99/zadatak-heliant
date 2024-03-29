package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "statistika")
@Getter
@Setter
@NoArgsConstructor
public class Statistika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "broj_popunjenih_formulara")
    private Integer brojPopunjenihFormulara;

}
