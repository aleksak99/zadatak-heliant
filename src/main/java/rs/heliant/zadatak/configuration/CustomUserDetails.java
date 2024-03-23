package rs.heliant.zadatak.configuration;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    @Getter
    private final Integer id;
    private final String korisnickoIme;
    private final String lozinka;
    private final Collection<? extends GrantedAuthority> role;

    public CustomUserDetails(String korisnickoIme, String lozinka, Collection<? extends GrantedAuthority> role, Integer id) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.role = role;
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return lozinka;
    }

    @Override
    public String getUsername() {
        return korisnickoIme;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
