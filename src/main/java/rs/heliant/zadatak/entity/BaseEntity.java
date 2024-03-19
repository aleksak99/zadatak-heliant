package rs.heliant.zadatak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vreme_kreiranja")
    @CreationTimestamp
    private LocalDateTime vremeKreiranja;

    @Column(name = "vreme_poslednje_izmene")
    @UpdateTimestamp
    private LocalDateTime vremePoslednjeIzmene;

    @Column(name = "id_korisnik_kreirao")
    @CreatedBy
    private Integer korisnikKreirao;

    @Column(name = "id_korisnik_poslednji_azurirao")
    @LastModifiedBy
    private Integer korisnikPoslednjiAzurirao;

}
