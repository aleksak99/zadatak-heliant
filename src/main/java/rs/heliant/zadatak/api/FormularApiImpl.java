package rs.heliant.zadatak.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.FormularApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import rs.heliant.zadatak.service.FormularService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FormularApiImpl extends BaseApi implements FormularApi {
    private final FormularService formularService;
    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VratiFormularResponse> azurirajFormular(Integer id, KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest) {
        log.debug("Azuriranje formulara sa id-em {}. Podaci: {}", id, kreirajAzurirajFormularRequest);

        FormularDTO formular = formularService.azurirajFormular(id, kreirajAzurirajFormularRequest);

        log.debug("Azurirani formular: {}", formular);
        return createSuccessResponse(new VratiFormularResponse().data(formular));
    }

    @Override
    @PreAuthorize("hasAuthority('RADNIK')")
    public ResponseEntity<VratiPopunjenFormularResponse> azurirajPopunjeniFormular(Integer id, PopuniFormularRequest popuniFormularRequest) {
        log.debug("Azuriranje popunjenog formulara sa id-em {}. Podaci: {}", id, popuniFormularRequest);

        PopunjenFormularDTO formular = formularService.azurirajPopunjeniFormular(id, popuniFormularRequest);

        log.debug("Azurirani formular: {}", formular);
        return createSuccessResponse(new VratiPopunjenFormularResponse().data(formular));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VratiFormularResponse> kreirajFormular(KreirajAzurirajFormularRequest kreirajAzurirajFormularRequest) {
        log.debug("Kreiranje formulara. Podaci: {}", kreirajAzurirajFormularRequest);

        FormularDTO formular = formularService.kreirajFormular(kreirajAzurirajFormularRequest);

        log.debug("Kreirani formular: {}", formular);
        return createCreatedResponse(new VratiFormularResponse().data(formular));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> obrisiFormular(Integer id) {
        log.debug("Brisanje formulara sa id-em {}.", id);

        formularService.obrisiFormular(id);

        log.debug("Formular sa id-em {} je obrisan", id);
        return createNoContentResponse();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> obrisiPopunjenFormular(Integer id) {
        log.debug("Brisanje popunjenog formulara sa id-em {}.", id);

        formularService.obrisiPopunjeniFormular(id);

        log.debug("Popunjeni formular sa id-em {} je obrisan", id);
        return createNoContentResponse();
    }

    @Override
    @PreAuthorize("hasAuthority('RADNIK')")
    public ResponseEntity<VratiPopunjenFormularResponse> popuniFormular(Integer id, PopuniFormularRequest popuniFormularRequest) {
        log.debug("Popunjavanje formulara {}.", id);

        PopunjenFormularDTO popunjenFormular = formularService.popuniFormular(id, popuniFormularRequest);

        log.debug("Formular: {}", popunjenFormular);
        return createCreatedResponse(new VratiPopunjenFormularResponse().data(popunjenFormular));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RADNIK')")
    public ResponseEntity<VratiFormularResponse> vratiFormularPoId(Integer id) {
        log.debug("Dohvatanje formulara po id={}.", id);

        FormularDTO formular = formularService.vratiFormularPoId(id);

        log.debug("Formular: {}", formular);
        return createSuccessResponse(new VratiFormularResponse().data(formular));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RADNIK')")
    public ResponseEntity<VratiPopunjenFormularResponse> vratiPopunjeniFormular(Integer id) {
        log.debug("Dohvatanje popunjenog formulara po id={}.", id);

        PopunjenFormularDTO formular = formularService.vratiPopunjeniFormularPoId(id);

        log.debug("Popunjeni formular: {}", formular);
        return createSuccessResponse(new VratiPopunjenFormularResponse().data(formular));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RADNIK')")
    public ResponseEntity<VratiFormulareResponse> vratiSveFormulare(Integer brojStranice, Integer brojElemenata) {
        log.debug("Dohvatanje liste formulara. brojStranice: {}, brojElemenata: {}", brojStranice, brojElemenata);

        List<FormularDTO> formulari = formularService.vratiFormulare(brojStranice, brojElemenata);

        log.debug("Lista formulari: {}", formulari);
        return createSuccessResponse(new VratiFormulareResponse().data(formulari));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RADNIK')")
    public ResponseEntity<VratiPopunjeneFormulareResponse> vratiSvePopunjeneFormulare(Integer id, Integer brojStranice, Integer brojElemenata) {
        log.debug("Dohvatanje liste popunjenih formulara za formular {}. brojStranice: {}, brojElemenata: {}", id, brojStranice, brojElemenata);

        List<PopunjenFormularDTO> formulari = formularService.vratiPopunjeneFormulare(id, brojStranice, brojElemenata);

        log.debug("Lista formulari: {}", formulari);
        return createSuccessResponse(new VratiPopunjeneFormulareResponse().data(formulari));
    }
}
