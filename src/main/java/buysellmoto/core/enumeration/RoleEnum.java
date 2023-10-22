package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    STAFF("STAFF"),
    CUSTOMER("CUSTOMER"),
    INVALID("INVALID");

    private final String code;

    public static RoleEnum of(String code) {
        return Arrays.stream(RoleEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
