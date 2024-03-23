package rs.heliant.zadatak.service;

import org.openapitools.model.KreirajAzurirajPoljeRequest;
import org.openapitools.model.PoljeDTO;
import rs.heliant.zadatak.entity.Polje;

import java.util.List;

public interface PoljeService {
    PoljeDTO azurirajPolje(Integer poljeId, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest);

    PoljeDTO kreirajPolje(Integer formularId, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest);

    List<PoljeDTO> vratiPolja(Integer formularId);

    PoljeDTO vratiPolje(Integer poljeId);

    void obrisiPolje(Integer poljeId);

    Polje nadjiPolje(Integer poljeId);
}
