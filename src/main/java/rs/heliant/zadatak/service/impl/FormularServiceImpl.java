package rs.heliant.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.heliant.zadatak.entity.Statistika;
import rs.heliant.zadatak.repository.FormularRepository;
import rs.heliant.zadatak.repository.StatistikaRepository;
import rs.heliant.zadatak.service.FormularService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormularServiceImpl implements FormularService {

    private final FormularRepository formularRepository;
    private final StatistikaRepository statistikaRepository;

    @Override
    @Transactional
    public void izracunajBrojPopunjenihFormulara(LocalDateTime datumPocetka, LocalDateTime datumZavrsetka) {
        log.debug("Izracunavanje broja popunjenih formulara za period od {} do {} je zapoceto.", datumPocetka, datumZavrsetka);
        Integer brojPopunjenihFormulara = formularRepository.izracunajBrojPopunjenihFormulara(datumPocetka, datumZavrsetka);
        Statistika statistika = new Statistika();
        statistika.setDatum(LocalDate.now().minusDays(1));
        statistika.setBrojPopunjenihFormulara(brojPopunjenihFormulara);
        statistikaRepository.save(statistika);
        log.debug("Izracunavanje broja popunjenih formulara za period od {} do {} je zavrseno.", datumPocetka, datumZavrsetka);
    }
}
