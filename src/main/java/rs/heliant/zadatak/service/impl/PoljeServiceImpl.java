package rs.heliant.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.KreirajAzurirajPoljeRequest;
import org.openapitools.model.PoljeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.heliant.zadatak.entity.Formular;
import rs.heliant.zadatak.entity.Polje;
import rs.heliant.zadatak.enums.ResponseCode;
import rs.heliant.zadatak.exception.BusinessValidationException;
import rs.heliant.zadatak.exception.InternalServerErrorException;
import rs.heliant.zadatak.mapper.FormularMapper;
import rs.heliant.zadatak.repository.PoljeRepository;
import rs.heliant.zadatak.service.FormularService;
import rs.heliant.zadatak.service.PoljeService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PoljeServiceImpl implements PoljeService {

    private final PoljeRepository poljeRepository;
    private final FormularMapper formularMapper;
    private final FormularService formularService;

    /**
     *
     * @param poljeId ID polja
     * @param kreirajAzurirajPoljeRequest Zahtev za azuriranje polja.
     * @return Azurirano polje
     */
    @Override
    public PoljeDTO azurirajPolje(Integer poljeId, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest) {
        try {
            Polje poljeEntitet = nadjiPolje(poljeId);
            formularMapper.azurirajPolje(poljeEntitet, kreirajAzurirajPoljeRequest);
            poljeEntitet = poljeRepository.save(poljeEntitet);
            return formularMapper.poljeUPoljeDTO(poljeEntitet);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     * @param formularId ID formulara
     * @param kreirajAzurirajPoljeRequest Zahtev za kreiranje polja
     * @return Kreirano polje
     */
    @Override
    @Transactional
    public PoljeDTO kreirajPolje(Integer formularId, KreirajAzurirajPoljeRequest kreirajAzurirajPoljeRequest) {
        try {
            Formular formular = formularService.nadjiFormular(formularId);
            Polje polje = formularMapper.kreirajPolje(kreirajAzurirajPoljeRequest, formular);
            polje = poljeRepository.save(polje);
            return formularMapper.poljeUPoljeDTO(polje);
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param formularId ID formulara
     * @return Lista polja
     */
    @Override
    @Transactional(readOnly = true)
    public List<PoljeDTO> vratiPolja(Integer formularId) {
        try {
            Formular formular = formularService.nadjiFormular(formularId);
            return formularMapper.kreirajListuPolja(formular.getPolja());
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param poljeId ID polja
     * @return Pronadjeno polje
     */
    @Override
    public PoljeDTO vratiPolje(Integer poljeId) {
        try {
            Polje polje = nadjiPolje(poljeId);
            return formularMapper.poljeUPoljeDTO(polje);
        } catch (Exception e) {
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    /**
     *
     * @param poljeId ID polja
     */
    @Override
    public void obrisiPolje(Integer poljeId) {
        try {
            poljeRepository.delete(nadjiPolje(poljeId));
        } catch (Exception e) {
            log.warn("Desila se greska", e);
            throw new InternalServerErrorException(ResponseCode.GENERAL_ERROR);
        }
    }

    public Polje nadjiPolje(Integer poljeId) {
        return poljeRepository.findById(poljeId).orElseThrow(() -> {
            log.warn("Polje ne postoji na formularu.");
            return new BusinessValidationException(ResponseCode.FORM_DOES_NOT_EXIST);
        });
    }

}
