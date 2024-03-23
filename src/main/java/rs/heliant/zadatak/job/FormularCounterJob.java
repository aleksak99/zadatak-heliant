package rs.heliant.zadatak.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.heliant.zadatak.service.FormularService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class FormularCounterJob {

    private final FormularService formularService;

    @Scheduled(cron = "${zadatak.formular-job.cron}")
    public void execute() {
        log.debug("Izvrsavanje job-a zapoceto.");
        LocalDateTime datumPocetka = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime datumZavrsetka = LocalDate.now().minusDays(1).atTime(23, 59, 59);
        formularService.izracunajBrojPopunjenihFormulara(datumPocetka, datumZavrsetka);
        log.debug("Izvrsavanje job-a zavrseno.");
    }

}
