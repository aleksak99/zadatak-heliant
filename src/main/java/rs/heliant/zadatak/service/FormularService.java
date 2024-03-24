package rs.heliant.zadatak.service;

import org.openapitools.model.FormularDTO;
import org.openapitools.model.KreirajAzurirajFormularRequest;
import org.openapitools.model.PopuniFormularRequest;
import org.openapitools.model.PopunjenFormularDTO;
import rs.heliant.zadatak.entity.Formular;

import java.time.LocalDateTime;
import java.util.List;

public interface FormularService {

    void izracunajBrojPopunjenihFormulara(LocalDateTime localDateTime, LocalDateTime dateTime);

    List<FormularDTO> vratiFormulare(Integer brojStranice, Integer brojElemenata);

    FormularDTO kreirajFormular(KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest);

    FormularDTO azurirajFormular(Integer id, KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest);

    void obrisiFormular(Integer id);

    FormularDTO vratiFormularPoId(Integer id);

    Formular nadjiFormular(Integer id);

    PopunjenFormularDTO popuniFormular(Integer id, PopuniFormularRequest request);

    List<PopunjenFormularDTO> vratiPopunjeneFormulare(Integer id, Integer brojStranice, Integer brojElemenata);

    PopunjenFormularDTO azurirajPopunjeniFormular(Integer id, PopuniFormularRequest popuniFormularRequest);

    PopunjenFormularDTO vratiPopunjeniFormularPoId(Integer id);

    void obrisiPopunjeniFormular(Integer id);
}
