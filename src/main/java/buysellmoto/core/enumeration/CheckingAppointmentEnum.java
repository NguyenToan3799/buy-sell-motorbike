package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CheckingAppointmentEnum {


    ACTIVE("ACTIVE", "ACTIVE"),
    IN_ACTIVE("IN_ACTIVE", "IN_ACTIVE"),
    INVALID("INVALID","Sai status");

    private final String code;
    private final String description;

    public static CheckingAppointmentEnum of(String code) {
        return Arrays.stream(CheckingAppointmentEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }
}
