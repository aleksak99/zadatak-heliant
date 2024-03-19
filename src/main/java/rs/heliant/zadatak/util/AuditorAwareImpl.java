package rs.heliant.zadatak.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rs.heliant.zadatak.entity.Korisnik;
import rs.heliant.zadatak.repository.KorisnikRepository;

import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Integer> {

    private final KorisnikRepository korisnikRepository;

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(authentication.getName()).orElse(new Korisnik());
        return Optional.ofNullable(korisnik.getId());
    }
}
