package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RequestTypeEnum {

    SELL_REQUEST("SELL_REQUEST"),
    BUY_REQUEST("BUY_REQUEST"),
    INVALID("INVALID");

    private final String code;

    public static RequestTypeEnum of(String code) {
        return Arrays.stream(RequestTypeEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
