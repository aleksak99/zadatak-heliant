package rs.heliant.zadatak.service;

public interface JwtService {
    String generateToken(String korisnickoIme);

    String getUsername(String token);

}
