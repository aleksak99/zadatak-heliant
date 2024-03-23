package rs.heliant.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.heliant.zadatak.entity.Polje;

@Repository
public interface PoljeRepository extends JpaRepository<Polje, Integer> {
}
