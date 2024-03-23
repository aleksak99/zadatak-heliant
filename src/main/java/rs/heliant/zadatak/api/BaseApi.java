package rs.heliant.zadatak.api;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.heliant.zadatak.enums.ResponseCode;

import java.lang.reflect.Field;

public class BaseApi {

    public <T> ResponseEntity<T> createSuccessResponse(T data) {
        setCodeAndMessage(data, ResponseCode.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    public <T> ResponseEntity<T> createCreatedResponse(T data) {
        setCodeAndMessage(data, ResponseCode.SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    public <T> ResponseEntity<T> createNoContentResponse(T data) {
        setCodeAndMessage(data, ResponseCode.SUCCESS);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(data);
    }

    private <T> void setCodeAndMessage(T data, ResponseCode responseCode) {
        try {
            Class<?> clazz = data.getClass();
            Field code = clazz.getDeclaredField("code");
            Field message = clazz.getDeclaredField("message");
            code.setAccessible(true);
            message.setAccessible(true);
            code.set(data, responseCode.getCode());
            message.set(data, responseCode.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

}
