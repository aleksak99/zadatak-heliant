package rs.heliant.zadatak.exception;

import lombok.Getter;
import lombok.Setter;
import rs.heliant.zadatak.enums.ResponseCode;

@Getter
@Setter
public class InternalServerErrorException extends RuntimeException implements ErrorCodeProvider {

    private final Integer code;
    private final String message;

    public InternalServerErrorException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }
}
