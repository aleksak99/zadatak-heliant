package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "formular_popunjen")
@Getter
@Setter
@NoArgsConstructor
public class FormularPopunjen extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_formular")
    private Formular formular;

    @OneToMany(mappedBy = "formularPopunjen", cascade = CascadeType.ALL)
    private List<PoljePopunjeno> popunjenaPolja;

}
