package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PostStatusEnum {


    ACTIVE("ACTIVE", "Bài đăng còn hạn"),

    SCHEDULED("SCHEDULED", "Đã có lịch checking xe"),

    EXPIRED("EXPIRED", "Bài đăng đã hết hạn"),

    COMPLETED("COMPLETED", "Đã hoàn tất"),

    INVALID("INVALID","Sai status");

    private final String code;
    private final String description;

    public static PostStatusEnum of(String code) {
        return Arrays.stream(PostStatusEnum.values())
                .filter(z -> z.getCode().equals(code))
                .findFirst()
                .orElse(INVALID);
    }

}
