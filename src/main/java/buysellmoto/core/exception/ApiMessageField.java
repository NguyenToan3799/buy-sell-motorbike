package buysellmoto.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiMessageField implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String fieldValue;
    private String fieldType;

    /* error-code for specific field */
    private String message;

    private Object object;
    private String position;

    private Integer lengthMin;
    private Integer lengthMax;

    public ApiMessageField(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
