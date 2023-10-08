package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SellRequestEnum {

    READY("READY"),
    INVALID("INVALID");

    private final String code;

    public static SellRequestEnum of(String code) {
        return Arrays.stream(SellRequestEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
