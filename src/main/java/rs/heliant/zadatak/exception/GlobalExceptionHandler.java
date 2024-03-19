package rs.heliant.zadatak.exception;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<BaseResponse> handleException(BusinessValidationException e) {
        return ResponseEntity.badRequest().body(createResponseBody(e));
    }

    private <T extends RuntimeException & ErrorCodeProvider> BaseResponse createResponseBody(T e) {
        log.error("Desila se greska.", e);
        return new BaseResponse()
                .code(e.getCode())
                .message(e.getMessage());
    }

}
