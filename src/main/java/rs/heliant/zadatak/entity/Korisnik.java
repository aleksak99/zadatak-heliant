package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "korisnik_rola",
            joinColumns = @JoinColumn(name = "id_korisnik"),
            inverseJoinColumns = @JoinColumn(name = "id_rola")
    )
    List<Rola> role;

}
