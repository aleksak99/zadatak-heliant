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
    LOGIN_FAILED(102, "Neuspesna prijava na sistem."),
    INVALID_USERNAME(103, "Pogresno korisnicko ime."),
    FORM_DOES_NOT_EXIST(104, "Formular ne postoji."),
    FIELD_DOES_NOT_EXIST(105, "Polje ne postoji na formularu."),
    FILLED_FORM_DOES_NOT_EXIST(106, "Popunjeni formular ne postoji."),
    FILLED_FIELD_DOES_NOT_EXIST(107, "Popunjeno polje ne postoji."),
    ORDINAL_NUMBER_ALREADY_EXISTS(108, "Polje sa unetim prikaznim redosledom za dati formular vec postoji."),
    TEXT_FIELD_SENT_NUMBER(109, "Morate uneti tekstualnu vrednost u polje %s."),
    NUMBER_FIELD_SENT_TEXT(110, "Morate uneti numericku vrednost u polje %s."),

    //token
    INVALID_TOKEN_FORMAT(201, "Format tokena nije validan."),
    TOKEN_DOES_NOT_EXIST(202, "Token nije poslat."),
    TOKEN_EXPIRED(203, "Token je istekao.");

    private final Integer code;
    private final String message;
}
