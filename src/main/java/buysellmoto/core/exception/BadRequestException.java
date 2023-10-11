package buysellmoto.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
public class BadRequestException extends RuntimeException {

    private final String message;
    private final List<ApiMessageField> fields = new ArrayList<>();

    public BadRequestException(String errorCode) {
        super(errorCode);
        this.message = errorCode;
    }

    public BadRequestException(String errorCode, ApiMessageField field) {
        super(errorCode);
        this.message = errorCode;
        this.fields.add(field);
    }

    public BadRequestException(String errorCode, List<ApiMessageField> fields) {
        super(errorCode);
        this.message = errorCode;
        this.fields.addAll(fields);
    }

    public BadRequestException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }

    public BadRequestException(Throwable throwable, List<ApiMessageField> fields) {
        super(throwable);
        this.message = throwable.getMessage();
        this.fields.addAll(fields);
    }

    public BadRequestException(String errorCode, Throwable throwable) {
        super(errorCode, throwable);
        this.message = errorCode;
    }

    public BadRequestException(String errorCode, Throwable throwable, List<ApiMessageField> fields) {
        super(errorCode, throwable);
        this.message = errorCode;
        this.fields.addAll(fields);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public List<ApiMessageField> getFields() {
        return fields;
    }

}
