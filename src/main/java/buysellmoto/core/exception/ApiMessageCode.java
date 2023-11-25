package buysellmoto.core.exception;

public final class ApiMessageCode {

    public static final String DEACTIVATED_USER = "Tài khoản của bạn đã bị khoá";
    public static final String USER_NOT_EXIST = "User not exist";
    public static final String INVALID_ROLE = "Invalid Role";
    public static final String INVALID_STATUS_MOVING = "invalid_status_moving";
    public static final String INVALID_STATUS = "invalid_status";
    public static final String SELL_REQUEST_NOT_EXIST = "sell_request_not_exist";
    public static final String REQUIRED_ID = "required_id";

    // User error
    public static final String INVALID_LOGIN_IDENTITIES_OR_PASSWORD = "Sai thông tin tài khoản hoặc mật khẩu";


    //Format Error
    public static final String INVALID_DATE_FORMAT = "invalid_date_format";

    private ApiMessageCode() {
        throw new ConstructorException();
    }

}
