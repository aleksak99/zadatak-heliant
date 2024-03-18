package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "formular")
@Getter
@Setter
@NoArgsConstructor
public class Formular extends BaseEntity{

    @Column(name = "naziv")
    private String naziv;

    @OneToMany(mappedBy = "formular", cascade = CascadeType.ALL)
    private List<FormularPopunjen> popunjeniFormulari;

    @OneToMany(mappedBy = "formular", cascade = CascadeType.ALL)
    private List<Polje> polja;

}
