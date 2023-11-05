package buysellmoto.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "")
public class UnauthorizedException extends RuntimeException {

    private final String message;
    private final List<ApiMessageField> fields = new ArrayList<>();

    public UnauthorizedException(String errorCode) {
        super(errorCode);
        this.message = errorCode;
    }

    public UnauthorizedException(String errorCode, ApiMessageField field) {
        super(errorCode);
        this.message = errorCode;
        this.fields.add(field);
    }

    public UnauthorizedException(String errorCode, List<ApiMessageField> fields) {
        super(errorCode);
        this.message = errorCode;
        this.fields.addAll(fields);
    }

    public UnauthorizedException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }

    public UnauthorizedException(Throwable throwable, List<ApiMessageField> fields) {
        super(throwable);
        this.message = throwable.getMessage();
        this.fields.addAll(fields);
    }

    public UnauthorizedException(String errorCode, Throwable throwable) {
        super(errorCode, throwable);
        this.message = errorCode;
    }

    public UnauthorizedException(String errorCode, Throwable throwable, List<ApiMessageField> fields) {
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
