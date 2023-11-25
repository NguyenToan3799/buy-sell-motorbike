package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TransactionTypeEnum {

    DEPOSIT("DEPOSIT"),
    FINALISE("FINALISE"),
    SELLER_PAY("SELLER_PAY"),
    INTERNAL("INTERNAL"),

    INVALID("INVALID");

    private final String code;

    public static TransactionTypeEnum of(String code) {
        return Arrays.stream(TransactionTypeEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
