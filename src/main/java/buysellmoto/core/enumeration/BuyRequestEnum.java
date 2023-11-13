package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BuyRequestEnum {

    // Người bán
    DRAFT("DRAFT", "Bảng nháp"),
    CONFIRMED("CONFIRMED", "Xác nhận đã gửi yêu cầu tới showroom"),
    CANCELLED("CANCELLED", "Huỷ yêu cầu đã gửi hoặc bản nháp"),

    //Showroom
    APPROVED("APPROVED", "Chấp nhận yêu cầu bán xe"),
    REJECTED("REJECTED", "Từ chối yêu cầu bán xe"),
    COMPLETED("COMPLETED", "Hoàn tất nhận xe để bán"),

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
