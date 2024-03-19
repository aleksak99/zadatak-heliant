package rs.heliant.zadatak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //validacija korisnika
    INVALID_USERNAME(1, "Pogresno korisnicko ime."),

    //token
    INVALID_TOKEN_FORMAT(101, "Format tokena nije validan."),
    TOKEN_DOES_NOT_EXIST(102, "Token nije poslat."),
    TOKEN_EXPIRED(103, "Token je istekao.");

    private final Integer code;
    private final String message;
}
