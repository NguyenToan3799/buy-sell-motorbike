package buysellmoto.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getFieldErrors().stream()
                .map( fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "rejectedValue", String.valueOf(fieldError.getRejectedValue()),
                        "rejectedReason", String.valueOf(fieldError.getDefaultMessage())
                )).toList());
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.badRequest().body("Value is null!!!");
    }

}
