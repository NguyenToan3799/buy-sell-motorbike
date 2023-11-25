package buysellmoto.core.constants;

import buysellmoto.core.exception.ConstructorException;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    public static final String SYSTEM = "System";

    public static final String SYSTEM_ROLE_ITA = "ITA";
    public static final String SYSTEM_ROLE_GUEST = "GUEST";
    public static final List<String> SYSTEM_ROLES = Arrays.asList(SYSTEM_ROLE_ITA, SYSTEM_ROLE_GUEST);

    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";
    public static final String CREATED_DATE_TIME = "createdDatetime";
    public static final String UPDATED_DATE_TIME = "updatedDatetime";
    public static final String ADMINISTRATION = "Administration";

    public static final String SUCCESS = "acknowledge";
    public static final String HAVE_CHANGED = "have changed";

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String UNDERSCORE = "_";
    public static final String SLASH = "/";
    public static final String CIRCUMFLEX = "^";
    public static final String SEMICOLON = ";";
    public static final String COLON = ":";
    public static final String ASTERISK = "*";
    public static final String THREE_ASTERISK = "***";
    public static final String JST = "JST";
    public static final String NULL = "NULL";
    public static final String JSON_FILE = ".json";
    public static final String CSV_FILE = ".csv";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NOT_AVAILABLE = "N/A";
    public static final String STOPPED = "STOPPED";
    public static final String PROTOCOL_HTTP = "http://";
    public static final String PROTOCOL_HTTPS = "https://";
    public static final Long RE_VERSION_START = 1L;

    /* DO NOT UPDATE */
    public static final String ATTACHMENT = "attachment";
    public static final String SEPARATE = "|";
    public static final String SEPARATE_PATTERN = "\\|";

    public final static String REGEX_DATE = "^\\d{4}-\\d{2}-\\d{2}$";

    public final static String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final static String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public final static String STRING_NAME_FILE_PATTERN = "^[a-zA-Z0-9-_]+(\\\\.[a-zA-Z0-9]+)?$";
    public final static String STRING_NAME_FILE_LABEL_PATTERN = "LABEL_(\\w+)(IC|NM)_(\\d+)x(\\d+)_(\\d+)_(\\w+)";

    public final static String PASSWORD_PATTERN = "^(?=.*[!@#$%^&*()_+\\-=[\\\\]{};':\\\"\\\\\\\\|,.<>/?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*()_+\\-=[\\\\]{};':\\\"\\\\\\\\|,.<>/?`~]{8,}$";

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 50;
    public static final Integer DEFAULT_PAGE_SIZE_MAX = 500;

    public static final Integer CODE_LENGTH = 64;
    public static final Integer NAME_LENGTH = 256;
    public static final Integer DESCRIPTION_LENGTH = 2048;
    public static final Integer TYPE_LENGTH = 16;
    public static final Integer URL_LENGTH = 2048;
    public static final Integer DEFAULT_MIN = 0;

    /**
     * quality
     */
    public static final Integer MIN_OF_MEASUREMENT = 1;
    public static final String LOCATION = "LOCATION";
    public static final String TOOL_DRAFT = "TOOL_DRAFT";
    public static final String TOOL_ACTIVE = "TOOL_ACTIVE";
    public static final String TOOL_OBSOLETE = "TOOL_OBSOLETE";
    public static final String MANUAL_COMPLETION = "MANUAL_COMPLETION";
    public static final String SAMPLING_METHOD_PERCENTAGE = "percentage";
    public static final String AQL_LEVEL = "LVL";
    public static final String SPECIFIC_SAMPLING = "SPECIFIC_SAMPLING";
    public static final String QA_APPLY = "QA_APPLY";
    public static final String INSPECTION = "INSPECTION";

    /**
     * warehouse
     */
    public static final Double MIN_QUANTITY = 0D;
    public static final Double MIN_QUANTITY_TO_SEPARATE = 1D;

    /**
     * Quartz Job
     */
    public static final String PRODUCTION_MODULE = "productions";
    public static final String INTEGRITY_MODULE = "integration";

    /**
     * Email service
     */
    public static final Integer EMAIL_FILE_MAX_SIZE = 5120;
    public final static String EMAIL_CODE_PATTERN = "^[a-zA-Z0-9_]*$";

    private Constants() {
        throw new ConstructorException();
    }

}
