package buysellmoto.core.ultilities;

import buysellmoto.core.constants.Constants;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.exception.ConstructorException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@Log4j2
public final class StringUtil {

    /* always at the bottom */
    private StringUtil() {
        throw new ConstructorException();
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static String trim(String val, String defaultValue) {
        return StringUtils.isNotBlank(val) ? val.trim() : defaultValue;
    }

    public static String trim(String val) {
        return trim(val, StringUtils.EMPTY);
    }

    public static String camelToSnake(String str) {
        String result = "";
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);

        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result = result + Character.toLowerCase(ch);
            } else {
                result = result + ch;
            }
        }
        return result;
    }

    public static String snakeToCamel(String str) {
        StringBuilder builder = new StringBuilder(str);

        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }

        return builder.toString();
    }

    public static String snakeToPascal(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        StringBuilder builder = new StringBuilder(str);

        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }

        return builder.toString();
    }

    public static String validate(String str, String regexFormat, String msgErr) {
        str = str.trim();
        if (!Pattern.matches(regexFormat, str)) {
            throw new BusinessException(msgErr);
        }
        return str;
    }

    public static String listToString(List<String> sts) {
        AtomicReference<String> value = new AtomicReference<>("");
        sts.forEach(x -> value.set(x + Constants.COMMA + value));
        return value.toString();
    }


    public static String generateRandomString(int length) {
        return RandomStringUtils.random(length, Constants.CHARACTERS);
    }

    public static String getPrefixAndDateStr(String prefix, String timeZone) {
        LocalDateTime currentDate = LocalDateTime.now()
                .atZone(ZoneId.of(timeZone))
                .toLocalDateTime();

        return prefix + currentDate.format(DateTimeFormatter.ofPattern(DateTimeUtil.GENERATOR_FORMAT_STRING_TO_MINUTE));
    }

    public static String escapeSqlWildCards(String s) {
        char escapeChar = getEscapeChar();
        return s != null
                ? s.replace("%", escapeChar + "%").replace("_", escapeChar + "_")
                : null;
    }

    public static char getEscapeChar() {
        return '\\';
    }
}
