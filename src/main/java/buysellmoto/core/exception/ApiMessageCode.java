package buysellmoto.core.exception;

public final class ApiMessageCode {

    public static final String DEACTIVATED_USER = "Tài khoản của bạn đã bị khoá";
    public static final String USER_NOT_EXIST = "User not exist";
    public static final String INVALID_ROLE = "Invalid Role";
    public static final String INVALID_LOGIN_IDENTITIES_OR_PASSWORD = "Sai thông tin tài khoản hoặc mật khẩu";

    private ApiMessageCode() {
        throw new ConstructorException();
    }

}
