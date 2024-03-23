package rs.heliant.zadatak.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rs.heliant.zadatak.entity.Korisnik;
import rs.heliant.zadatak.enums.ResponseCode;
import rs.heliant.zadatak.exception.BusinessValidationException;
import rs.heliant.zadatak.repository.KorisnikRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final KorisnikRepository korisnikRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(username).orElseThrow(() -> {
            log.warn("Korisinik sa korisnickim imenom {} ne poztoji u sistemu.", username);
            return new BusinessValidationException(ResponseCode.INVALID_USERNAME);
        });
        return new CustomUserDetails(
                korisnik.getKorisnickoIme(),
                korisnik.getLozinka(),
                korisnik.getRole().stream().map(rola -> new SimpleGrantedAuthority(rola.getNaziv())).toList(),
                korisnik.getId()
        );
    }
}
