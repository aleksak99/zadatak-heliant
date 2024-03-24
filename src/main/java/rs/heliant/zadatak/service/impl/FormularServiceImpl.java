package rs.heliant.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.model.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.heliant.zadatak.entity.*;
import rs.heliant.zadatak.enums.ResponseCode;
import rs.heliant.zadatak.enums.TipPolja;
import rs.heliant.zadatak.exception.BusinessValidationException;
import rs.heliant.zadatak.exception.InternalServerErrorException;
import rs.heliant.zadatak.mapper.FormularMapper;
import rs.heliant.zadatak.repository.FormularPopunjenRepository;
import rs.heliant.zadatak.repository.FormularRepository;
import rs.heliant.zadatak.repository.PoljeRepository;
import rs.heliant.zadatak.repository.StatistikaRepository;
import rs.heliant.zadatak.service.FormularService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormularServiceImpl implements FormularService {

    private final FormularRepository formularRepository;
    private final StatistikaRepository statistikaRepository;
    private final FormularMapper formularMapper;
    private final PoljeRepository poljeRepository;
    private final FormularPopunjenRepository formularPopunjenRepository;

    /**
     *
     * @param datumPocetka Datum i vreme pocetka pretrage
     * @param datumZavrsetka Datum i vreme zavrsetka pretrage
     */
    @Override
    public void izracunajBrojPopunjenihFormulara(LocalDateTime datumPocetka, LocalDateTime datumZavrsetka) {
        try {
            Integer brojPopunjenihFormulara = formularRepository.izracunajBrojPopunjenihFormulara(datumPocetka, datumZavrsetka);
            Statistika statistika = new Statistika();
            statistika.setDatum(LocalDate.now().minusDays(1));
            statistika.setBrojPopunjenihFormulara(brojPopunjenihFormulara);
            statistikaRepository.save(statistika);
        } catch (Exception e) {
            log.error("Desila se neocekivana greska.", e);
        }

    }

    /**
     *
     * @param brojStranice Broj stranice (offset)
     * @param brojElemenata Velicina stranice (limit)
     * @return Lista formulara
     */
    @Override
    public List<FormularDTO> vratiFormulare(Integer brojStranice, Integer brojElemenata) {
        try {
            List<Formular> formulariDB = formularRepository.findByOrderByVremeKreiranjaDesc(PageRequest.of((brojStranice - 1) * brojElemenata, brojElemenata));
            return formularMapper.listaFormularaUListuFolmularDTO(formulariDB);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param kreirajAzurirajFormularRequest Zahtev za kreiranje formulara.
     * @return Kreirani formular
     */
    @Override
    public FormularDTO kreirajFormular(KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest) {
        try {
            Formular formularEntitet = formularMapper.formularRequestUFormular(kreirajAzurirajFormularRequest);
            formularEntitet = formularRepository.save(formularEntitet);
            return formularMapper.formularEntitetUFormularDTO(formularEntitet);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     * @param id                             ID formulara
     * @param kreirajAzurirajFormularRequest Zahtev za azuriranje formulara.
     * @return Azurirani formular
     */
    @Override
    @Transactional
    public FormularDTO azurirajFormular(Integer id, KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest) {
        try {
            Formular formularEntitet = nadjiFormular(id);
            formularEntitet.setNaziv(kreirajAzurirajFormularRequest.getNaziv());
            formularEntitet = formularRepository.save(formularEntitet);
            return formularMapper.formularEntitetUFormularDTO(formularEntitet);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID formulara
     */
    @Override
    public void obrisiFormular(Integer id) {
        try {
            Formular formularEntitet = nadjiFormular(id);
            formularRepository.delete(formularEntitet);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID formulara
     * @return Pronadjeni formular
     */
    @Override
    public FormularDTO vratiFormularPoId(Integer id) {
        try {
            Formular formularEntitet = nadjiFormular(id);
            return formularMapper.formularEntitetUFormularDTO(formularEntitet);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    @Override
    @Transactional
    public PopunjenFormularDTO popuniFormular(Integer id, PopuniFormularRequest popuniFormularRequest) {
        try {
            Formular formular = nadjiFormular(id);
            validirajPolja(formular.getPolja(), popuniFormularRequest.getPolja(), false);
            FormularPopunjen formularPopunjen = new FormularPopunjen();
            formularPopunjen.setFormular(formular);

            List<Integer> poljeIds = popuniFormularRequest.getPolja().stream()
                    .map(PopuniFormularRequestPoljaInner::getId)
                    .collect(Collectors.toList());
            List<Polje> polja = poljeRepository.findAllById(poljeIds);

            List<PoljePopunjeno> popunjenaPolja = new ArrayList<>();
            for (PopuniFormularRequestPoljaInner popunjenoPoljeDTO: popuniFormularRequest.getPolja()) {

                Polje poljeEntity = polja.stream()
                        .filter(polje -> polje.getId().equals(popunjenoPoljeDTO.getId()))
                        .findFirst()
                        .orElseThrow(() -> new BusinessValidationException(ResponseCode.FIELD_DOES_NOT_EXIST));

                PoljePopunjeno poljePopunjeno = new PoljePopunjeno();
                poljePopunjeno.setFormularPopunjen(formularPopunjen);
                poljePopunjeno.setPolje(poljeEntity);
                poljePopunjeno.setVrednostBroj(popunjenoPoljeDTO.getVrednostBroj());
                poljePopunjeno.setVrednostTekst(popunjenoPoljeDTO.getVrednostTekst());
                popunjenaPolja.add(poljePopunjeno);
            }
            formularPopunjen.setPopunjenaPolja(popunjenaPolja);
            formularPopunjen = formularPopunjenRepository.save(formularPopunjen);
            return formularMapper.formularPopunjenUPopunjenFormularDTO(formularPopunjen);
        }
        catch (BusinessValidationException e) {
            throw e;
        }
        catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID formulara
     * @param brojStranice Broj stranice (offset)
     * @param brojElemenata Velicina stranice (limit)
     * @return  Lista popunjenih formulara
     */
    @Override
    public List<PopunjenFormularDTO> vratiPopunjeneFormulare(Integer id, Integer brojStranice, Integer brojElemenata) {
        try {
            List<FormularPopunjen> popunjeniFormulari = formularPopunjenRepository.pronadjiPopunjeneFormulare(nadjiFormular(id), PageRequest.of((brojStranice - 1) * brojElemenata, brojElemenata));
            return formularMapper.kreirajListuPopunjenihFormulara(popunjeniFormulari);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID popunjenog formulara
     * @param popuniFormularRequest Zahtev za azuriranje popunjenog formulara
     * @return Azurirani popunjeni formular
     */
    @Override
    @Transactional
    public PopunjenFormularDTO azurirajPopunjeniFormular(Integer id, PopuniFormularRequest popuniFormularRequest) {
        try {
            FormularPopunjen formularPopunjen = nadjiFormularPopunjen(id);
            validirajPolja(formularPopunjen.getFormular().getPolja(), popuniFormularRequest.getPolja(), true);
            for(PopuniFormularRequestPoljaInner popunjenoPolje: popuniFormularRequest.getPolja()) {
                PoljePopunjeno poljePopunjeno = formularPopunjen.getPopunjenaPolja().stream().filter(p -> p.getId().equals(popunjenoPolje.getId())).findFirst().orElseThrow(() -> new BusinessValidationException(ResponseCode.FILLED_FIELD_DOES_NOT_EXIST));
                formularMapper.azurirajPoljePopunjeno(poljePopunjeno, popunjenoPolje);
            }
            formularPopunjen = formularPopunjenRepository.save(formularPopunjen);
            return formularMapper.formularPopunjenUPopunjenFormularDTO(formularPopunjen);
        }
        catch (BusinessValidationException e) {
            throw e;
        }
        catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID popunjenog formulara
     * @return Pronadjeni popunjeni formular
     */
    @Override
    @Transactional(readOnly = true)
    public PopunjenFormularDTO vratiPopunjeniFormularPoId(Integer id) {
        try {
            FormularPopunjen formularPopunjen = nadjiFormularPopunjen(id);
            return formularMapper.formularPopunjenUPopunjenFormularDTO(formularPopunjen);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID popunjenog formulara koji se brise
     */
    @Override
    public void obrisiPopunjeniFormular(Integer id) {
        try {
            FormularPopunjen formularPopunjen = nadjiFormularPopunjen(id);
            formularPopunjenRepository.delete(formularPopunjen);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param id ID formulara
     * @return Pronadjeni formular
     */
    public Formular nadjiFormular(Integer id) {
        return formularRepository.findById(id).orElseThrow(() -> {
            log.warn("Formular ne postoji.");
            return new BusinessValidationException(ResponseCode.FORM_DOES_NOT_EXIST);
        });
    }

    private FormularPopunjen nadjiFormularPopunjen(Integer id) {
        return formularPopunjenRepository.findById(id).orElseThrow(() -> {
            log.warn("Popunjeni formular ne postoji.");
            return new BusinessValidationException(ResponseCode.FILLED_FORM_DOES_NOT_EXIST);
        });
    }

    private void validirajPolja(List<Polje> formularPolja, List<PopuniFormularRequestPoljaInner> polja, boolean azuriranje) {
        polja.forEach(polje -> {
            Polje formularPolje;
            if (azuriranje) {
                formularPolje = formularPolja.stream().filter(fp -> fp.getPopunjenaPolja().stream().anyMatch(pp -> pp.getId().equals(polje.getId()))).findFirst().orElseThrow(() -> new BusinessValidationException(ResponseCode.FIELD_DOES_NOT_EXIST));
            } else {
                formularPolje = formularPolja.stream().filter(fp -> fp.getId().equals(polje.getId())).findFirst().orElseThrow(() -> new BusinessValidationException(ResponseCode.FIELD_DOES_NOT_EXIST));
            }
            if (formularPolje.getTip().equals(TipPolja.BROJ) && (polje.getVrednostBroj() == null || StringUtils.isNotBlank(polje.getVrednostTekst()))) {
                throw new BusinessValidationException(ResponseCode.NUMBER_FIELD_SENT_TEXT, formularPolje.getNaziv());
            }
            if (formularPolje.getTip().equals(TipPolja.TEKST) && (polje.getVrednostBroj() != null || StringUtils.isBlank(polje.getVrednostTekst()))) {
                throw new BusinessValidationException(ResponseCode.TEXT_FIELD_SENT_NUMBER, formularPolje.getNaziv());
            }
        });
    }
}
