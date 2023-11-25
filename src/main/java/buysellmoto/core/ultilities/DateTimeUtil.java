package buysellmoto.core.ultilities;

import buysellmoto.core.constants.Constants;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.ApiMessageField;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.exception.ConstructorException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Log4j2
public final class DateTimeUtil {

    public static final String ZONE_UTC = "UTC";

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_UTC = "yyyy-MM-dd'T'";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_TIME_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String FORMAT_DATE_TIME_ZONE = "yyyy/MM/dd HH:mm:ss Z";
    public static final String FORMAT_DATE_SPLASH = "yyyy/MM/dd";

    public static final String TIME_START = "00:00:00";
    public static final String TIME_START_MS_UTC = "00:00:00.000Z";
    public static final String TIME_END = "23:59:59";
    public static final String TIME_END_MS_UTC = "23:59:59.999Z";
    public static final String GENERATOR_FORMAT_STRING = "yyyyMMddHHmmss";
    public static final String GENERATOR_FORMAT_STRING_TO_MINUTE = "yyyyMMddHHmm";


    /* always at the bottom */
    private DateTimeUtil() {
        throw new ConstructorException();
    }

    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String spent(long start) {
        return format(System.currentTimeMillis() - start);
    }

    public static String format(long duration) {
        long dy = TimeUnit.MILLISECONDS.toDays(duration);
        long hr = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long min = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long sec = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        long ms = TimeUnit.MILLISECONDS.toMillis(duration) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
        return String.format("%s %s %s %s %s",
                convert(dy, "Day"),
                convert(hr, "Hour"),
                convert(min, "Min"),
                convert(sec, "sec"),
                convert(ms, "ms")).trim();
    }

    private static String convert(long duration, String type) {
        return duration <= 0 ? StringUtils.EMPTY : "[" + String.valueOf(duration) + "] " + type;
    }

    public static LocalDateTime convertToLocalDateTime(Long duration) {
        return Objects.isNull(duration) ? null : duration <= 0 ? null : Instant.ofEpochMilli(duration)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTime(Long duration, ZoneId zone) {
        return Objects.isNull(duration) ? null : duration <= 0 ? null : Instant.ofEpochMilli(duration)
                .atZone(zone).toLocalDateTime();
    }

    public static Long convertToLong(LocalDateTime dateTime) {
        return Objects.isNull(dateTime) ? null : dateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static Long convertToLong(LocalDateTime dateTime, ZoneId zone) {
        return Objects.isNull(dateTime) ? null : dateTime.atZone(zone)
                .toInstant()
                .toEpochMilli();
    }

    public static Long getLocalDateTime(ZoneId zone) {
        return LocalDateTime.now().atZone(zone)
                .toInstant()
                .toEpochMilli();
    }

    public static LocalDateTime parseToLocalDateTime(String dateTime, ZoneId zoneId, String patternDate) {
        try {
            ZonedDateTime zonedDateTime = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern(patternDate))
                    .atStartOfDay()
                    .atZone(ObjectUtils.isEmpty(zoneId) ? ZoneId.systemDefault() : zoneId)
                    .withZoneSameInstant(ZoneId.systemDefault());
            return zonedDateTime.toLocalDateTime();
        } catch (Exception e) {
            throw new BusinessException(ApiMessageCode.INVALID_DATE_FORMAT, ApiMessageField.builder()
                            .fieldName("pattern_date_configured")
                            .fieldValue(patternDate)
                            .build());
        }
    }

    public static String parseDateStr(LocalDateTime localDate) {
        return ObjectUtils.isEmpty(localDate) || ObjectUtils.isEmpty(DateTimeUtil.FORMAT_DATE)
                ? Constants.EMPTY : DateTimeFormatter.ofPattern(DateTimeUtil.FORMAT_DATE).format(localDate);
    }

    public static String parseDateStr(LocalDateTime localDate, String formatDate) {
        return ObjectUtils.isEmpty(localDate) || ObjectUtils.isEmpty(formatDate)
                ? Constants.EMPTY : DateTimeFormatter.ofPattern(formatDate).format(localDate);
    }

    public static String getCurrentTimeToMinute(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(GENERATOR_FORMAT_STRING_TO_MINUTE));
    }

}
