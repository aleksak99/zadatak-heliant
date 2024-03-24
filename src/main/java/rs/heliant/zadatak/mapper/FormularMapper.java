package rs.heliant.zadatak.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.model.*;
import rs.heliant.zadatak.entity.Formular;
import rs.heliant.zadatak.entity.FormularPopunjen;
import rs.heliant.zadatak.entity.Polje;
import rs.heliant.zadatak.entity.PoljePopunjeno;

import java.util.Comparator;
import java.util.List;

@Mapper
public abstract class FormularMapper {

    public abstract List<FormularDTO> listaFormularaUListuFolmularDTO(List<Formular> formulari);

    public abstract Formular formularRequestUFormular(KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest);

    public abstract FormularDTO formularEntitetUFormularDTO(Formular formularEntitet);

    public abstract void azurirajPolje(@MappingTarget Polje poljeEntitet, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest);

    public abstract PoljeDTO poljeUPoljeDTO(Polje polje);

    @Mapping(target = "naziv", source = "kreirajAzurirajPoljeRequest.naziv")
    @Mapping(target = "tip", source = "kreirajAzurirajPoljeRequest.tip")
    @Mapping(target = "prikazniRedosled", source = "kreirajAzurirajPoljeRequest.prikazniRedosled")
    @Mapping(target = "formular", source = "formular")
    public abstract Polje kreirajPolje(KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest, Formular formular);

    public abstract List<PoljeDTO> kreirajListuPolja(List<Polje> polja);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "popunjenaPolja", source = "popunjenaPolja")
    public abstract PopunjenFormularDTO formularPopunjenUPopunjenFormularDTO(FormularPopunjen formularPopunjen);

    @Mapping(target = "tip", source = "polje.tip")
    @Mapping(target = "prikazniRedosled", source = "polje.prikazniRedosled")
    @Mapping(target = "naziv", source = "polje.naziv")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "vrednostTekst", source = "vrednostTekst")
    @Mapping(target = "vrednostBroj", source = "vrednostBroj")
    public abstract PopunjenoPoljeDTO poljePopunjenoUPopunjenoPoljeDTO(PoljePopunjeno poljePopunjeno);

    public abstract List<PopunjenFormularDTO> kreirajListuPopunjenihFormulara(List<FormularPopunjen> popunjeniFormulari);

    public abstract List<PopunjenoPoljeDTO> mapirajListuPopunjenihPolja(List<PoljePopunjeno> popunjenaPolja);

    public abstract void azurirajPoljePopunjeno(@MappingTarget PoljePopunjeno poljePopunjeno, PopuniFormularRequestPoljaInner popunjenoPolje);

    @AfterMapping
    protected void afterMapping1(@MappingTarget List<PopunjenoPoljeDTO> popunjenaPolja) {
        popunjenaPolja.sort(Comparator.comparingInt(PopunjenoPoljeDTO::getPrikazniRedosled));
    }

    @AfterMapping
    protected void afterMapping2(@MappingTarget List<PoljeDTO> polja) {
        polja.sort(Comparator.comparingInt(PoljeDTO::getPrikazniRedosled));
    }
}
