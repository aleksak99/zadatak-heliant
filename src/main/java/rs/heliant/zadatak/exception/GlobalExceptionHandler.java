package rs.heliant.zadatak.exception;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BaseResponse;
import org.openapitools.model.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<BaseResponse> handleException(BusinessValidationException e) {
        return ResponseEntity.badRequest().body(createResponseBody(e));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<BaseResponse> handleException(InternalServerErrorException e) {
        return ResponseEntity.internalServerError().body(createResponseBody(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setMessage("Validation failed.");

        ex.getBindingResult().getAllErrors().forEach(error -> validationErrorResponse.getErrors().put(((FieldError) error).getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(validationErrorResponse);
    }

    private <T extends RuntimeException & ErrorCodeProvider> BaseResponse createResponseBody(T e) {
        log.error("Desila se greska.", e);
        return new BaseResponse()
                .code(e.getCode())
                .message(e.getMessage());
    }

}
