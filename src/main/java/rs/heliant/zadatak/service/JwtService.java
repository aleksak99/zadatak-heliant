package rs.heliant.zadatak.service;

import java.util.Date;

public interface JwtService {
    String generateToken(String korisnickoIme);

    String getUsername(String token);

}
