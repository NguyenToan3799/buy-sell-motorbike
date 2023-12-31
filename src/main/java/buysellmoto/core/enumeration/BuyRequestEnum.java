package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BuyRequestEnum {

    // Người bán
    CREATED("CREATED", "Đã gửi"),
    CANCELLED("CANCELLED", "Huỷ yêu cầu đã gửi"),

    //Showroom
    CONFIRMED("CONFIRMED", "Xác nhận xem xe"),
    DEPOSITED("DEPOSITED", "Xác nhận mua xe và đã cọc"),
    SCHEDULED("SCHEDULED", "Đã sắp lịch hoàn tất thủ tục và bàn giao xe"),
    COMPLETED("COMPLETED", "Đã tất toán xe"),

    INVALID("INVALID","Sai status");

    private final String code;
    private final String description;

    public static BuyRequestEnum of(String code) {
        return Arrays.stream(BuyRequestEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
