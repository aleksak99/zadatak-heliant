package rs.heliant.zadatak.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.heliant.zadatak.entity.Formular;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FormularRepository extends JpaRepository<Formular, Integer> {

    @Query(value = "SELECT COUNT(*) FROM formular_popunjen WHERE vreme_kreiranja BETWEEN :datumPocetka AND :datumZavrsetka", nativeQuery = true)
    Integer izracunajBrojPopunjenihFormulara(@Param("datumPocetka") LocalDateTime datumPocetka, @Param("datumZavrsetka") LocalDateTime datumZavrsetka);

    List<Formular> findByOrderByVremeKreiranjaDesc(PageRequest pageRequest);
}
