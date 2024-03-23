package rs.heliant.zadatak.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PoljeApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import rs.heliant.zadatak.service.PoljeService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PoljeApiImpl extends BaseApi implements PoljeApi {

    private final PoljeService poljeService;

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VratiPoljeResponse> azurirajPolje(Integer poljeId, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest) {
        log.debug("Azuriranje polja {}. Podaci: {}", poljeId, kreirajAzurirajPoljeRequest);

        PoljeDTO polje = poljeService.azurirajPolje(poljeId, kreirajAzurirajPoljeRequest);

        log.debug("Azurirano polje: {}", polje);
        return createSuccessResponse(new VratiPoljeResponse().data(polje));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VratiPoljeResponse> kreirajPolje(Integer formularId, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest) {
        log.debug("Kreiranje polja na formularu {}. Podaci: {}", formularId, kreirajAzurirajPoljeRequest);

        PoljeDTO polje = poljeService.kreirajPolje(formularId, kreirajAzurirajPoljeRequest);

        log.debug("Kreirano polje: {}", polje);
        return createCreatedResponse(new VratiPoljeResponse().data(polje));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse> obrisiPolje(Integer poljeId) {
        log.debug("Brisanje polja {}.", poljeId);

        poljeService.obrisiPolje(poljeId);

        log.debug("Obrisano polje: {}", poljeId);
        return createNoContentResponse(new BaseResponse());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RADNIK')")
    public ResponseEntity<VratiListuPoljaResponse> vratiPoljaFormulara(Integer formularId) {
        log.debug("Vracanje polja na formularu {}.", formularId);

        List<PoljeDTO> polja = poljeService.vratiPolja(formularId);

        log.debug("Lista polja: {}", polja);
        return createCreatedResponse(new VratiListuPoljaResponse().data(polja));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<VratiPoljeResponse> vratiPoljePoId(Integer poljeId) {
        log.debug("Vracanje polja {}.", poljeId);

        PoljeDTO polje = poljeService.vratiPolje(poljeId);

        log.debug("Azurirano polje: {}", polje);
        return createSuccessResponse(new VratiPoljeResponse().data(polje));
    }
}
