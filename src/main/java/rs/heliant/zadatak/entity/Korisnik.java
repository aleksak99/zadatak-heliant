package rs.heliant.zadatak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "korisnik")
@Getter
@Setter
@NoArgsConstructor
public class Korisnik extends BaseEntity {

    @Column(name = "korisnicko_ime")
    private String korisnickoIme;

    @Column(name = "lozinka")
    private String lozinka;

}
