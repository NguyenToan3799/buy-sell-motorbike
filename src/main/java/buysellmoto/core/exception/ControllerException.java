package buysellmoto.core.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@Log4j2
@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getFields());
    }

    /* 400 */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getFields());
    }

    /* 500 */
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleInternalServerErrorException(InternalServerErrorException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getFields());
    }

    /* 401 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        FieldError e = ex.getBindingResult().getFieldError();

        List<ApiMessageField> fields = Arrays.asList(ApiMessageField.builder()
                .fieldName(e.getField())
                .object(e.getRejectedValue())
                .message(e.getDefaultMessage())
                .build());

        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), fields);
    }

}
