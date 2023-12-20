package buysellmoto.core.exception;

public final class ApiMessageCode {

    public static final String DEACTIVATED_USER = "Tài khoản của bạn đã bị khoá";
    public static final String USER_NOT_EXIST = "User not exist";
    public static final String INVALID_ROLE = "Invalid Role";
    public static final String INVALID_STATUS_MOVING = "invalid_status_moving";
    public static final String INVALID_STATUS = "invalid_status";
    public static final String SELL_REQUEST_NOT_EXIST = "sell_request_not_exist";
    public static final String REQUIRED_ID = "required_id";
    public static final String SELL_REQUEST_ID_REQUIRED = "sell_request_id_required";
    public static final String BUY_REQUEST_ID_REQUIRED = "buy_request_id_required";
    public static final String USER_NAME_EXIST = "user_name_exist";
    public static final String PHONE_EXIST = "phone_exist";
    public static final String EMAIL_EXIST = "email_exist";
    public static final String WRONG_OLD_PASSWORD = "wrong_old_password";
    public static final String NEW_PASSWORD_NOT_MATCH = "new_password_not_match";
    public static final String COMMENTATOR_TYPE_INVALID = "commentator_type_invalid";
    public static final String CAN_NOT_BUY_YOUR_CAR = "Bạn không thể mua xe của chính mình!";

    // User error
    public static final String INVALID_LOGIN_IDENTITIES_OR_PASSWORD = "Sai thông tin tài khoản hoặc mật khẩu";

    //Format Error
    public static final String INVALID_DATE_FORMAT = "invalid_date_format";

    private ApiMessageCode() {
        throw new ConstructorException();
    }

}
