package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MotorTypeEnum {

    // Người bán
    MANUAL("MANUAL"),
    AUTOMATIC("AUTOMATIC"),
    CLUTCH ("CLUTCH"),
    INVALID("INVALID");

    private final String code;

    public static MotorTypeEnum of(String code) {
        return Arrays.stream(MotorTypeEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
