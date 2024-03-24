package rs.heliant.zadatak.exception;

import lombok.Getter;
import lombok.Setter;
import rs.heliant.zadatak.enums.ResponseCode;

@Getter
@Setter
public class BusinessValidationException extends RuntimeException implements ErrorCodeProvider {

    private final Integer code;
    private final String message;

    public BusinessValidationException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public BusinessValidationException(ResponseCode responseCode, String additionalMessage) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = String.format(responseCode.getMessage(), additionalMessage);
    }


}
