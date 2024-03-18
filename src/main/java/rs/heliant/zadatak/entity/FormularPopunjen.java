package rs.heliant.zadatak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "formular_popunjen")
@Getter
@Setter
@NoArgsConstructor
public class FormularPopunjen extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_formular")
    private Formular formular;

}
