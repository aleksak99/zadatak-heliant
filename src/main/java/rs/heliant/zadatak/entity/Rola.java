package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rola")
@Getter
@Setter
@NoArgsConstructor
public class Rola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @ManyToMany(mappedBy = "role")
    List<Korisnik> korisnici;
}
