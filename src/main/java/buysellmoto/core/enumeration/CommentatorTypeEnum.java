package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CommentatorTypeEnum {

    CUSTOMER("CUSTOMER", "CUSTOMER"),
    SHOWROOM("SHOWROOM", "SHOWROOM"),
    INVALID("INVALID","Sai status");

    private final String code;
    private final String description;

    public static CommentatorTypeEnum of(String code) {
        return Arrays.stream(CommentatorTypeEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
