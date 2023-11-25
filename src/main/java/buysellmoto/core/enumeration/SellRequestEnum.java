package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SellRequestEnum {

    // Người bán
//    DRAFT("DRAFT", "Bảng nháp"),
//    CONFIRMED("CONFIRMED", "Xác nhận đã gửi yêu cầu tới showroom"),
    CREATED("CREATED", "Đã gửi yêu cầu tới showroom"),
    CANCELLED("CANCELLED", "Huỷ yêu cầu đã gửi hoặc bản nháp"),

    //Showroom
    APPROVED("APPROVED", "Chấp nhận yêu cầu bán xe"),
    REJECTED("REJECTED", "Từ chối yêu cầu bán xe"),

    CHECKED("CHECKED", "Đã kiểm tra và nhận xe"),
    POSTED("POSTED", "Đã đăng bài"),

    EXPIRED("EXPIRED", "Bài đăng hết hạn"),

    COMPLETED("COMPLETED", "Hoàn tất bán xe"),

    INVALID("INVALID","Sai status");

    private final String code;
    private final String description;

    public static SellRequestEnum of(String code) {
        return Arrays.stream(SellRequestEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
