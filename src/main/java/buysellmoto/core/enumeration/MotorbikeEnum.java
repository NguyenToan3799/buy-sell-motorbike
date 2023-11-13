package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MotorbikeEnum {

    // Người bán
    DRAFT("DRAFT", "Bảng nháp"),
    IN_SELL_REQUEST("IN_SELL_REQUEST", "Xe mới đuọc gửi yêu cầu"),
    APPROVED("APPROVED", "Chấp nhận yêu cầu bán xe"),
    REJECTED("REJECTED", "Từ chối yêu cầu bán xe"),

    //Showroom
    READY_TO_SELL("READY_TO_SELL", "Xe đã nhận bán, có thể đăng bài"),

    //Người mua
    AWAITING_VIEWING("AWAITING_VIEWING", "Xe đang chờ người mua đến xem"),
    AWAITING_PAYMENT("AWAITING_PAYMENT", "Người mua đồng ý mua xe. Chờ giao dịch hoàn tất"),

    SOLD_OUT("SOLD_OUT", "Xe đã bán"),

    INVALID("INVALID","Sai status");

    private final String code;
    private final String description;

    public static MotorbikeEnum of(String code) {
        return Arrays.stream(MotorbikeEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
