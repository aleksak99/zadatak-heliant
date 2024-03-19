package rs.heliant.zadatak.exception;

import lombok.Getter;
import lombok.Setter;
import rs.heliant.zadatak.enums.ErrorCode;

@Getter
@Setter
public class BusinessValidationException extends RuntimeException implements ErrorCodeProvider {

    private final Integer code;
    private final String message;

    public BusinessValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


}
