package rs.heliant.zadatak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //Uspesno izvrsene operacije
    SUCCESS(1, "Uspesno izvrsena operacija."),

    //validacije
    GENERAL_ERROR(101, "Desila se greska, dacemo sve od sebe da je sto pre otklonimo."),
    INVALID_USERNAME(102, "Pogresno korisnicko ime."),
    FORM_DOES_NOT_EXIST(103, "Formular ne postoji."),
    FIELD_DOES_NOT_EXIST(104, "Polje ne postoji na formularu."),
    FILLED_FORM_DOES_NOT_EXIST(105, "Popunjeni formular ne postoji."),
    FILLED_FIELD_DOES_NOT_EXIST(103, "Popunjeno polje ne postoji."),

    //token
    INVALID_TOKEN_FORMAT(201, "Format tokena nije validan."),
    TOKEN_DOES_NOT_EXIST(202, "Token nije poslat."),
    TOKEN_EXPIRED(203, "Token je istekao.");

    private final Integer code;
    private final String message;
}
