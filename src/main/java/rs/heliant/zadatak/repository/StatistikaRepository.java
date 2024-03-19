package rs.heliant.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.heliant.zadatak.entity.Statistika;

@Repository
public interface StatistikaRepository extends JpaRepository<Statistika, Integer> {
}
