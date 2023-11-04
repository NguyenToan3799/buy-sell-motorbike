package buysellmoto.core.exception;

public final class ApiMessageCode {

    public static final String DEACTIVATED_USER = "Deactivated User";
    public static final String USER_NOT_EXIST = "User not exist";
    public static final String INVALID_ROLE = "Invalid Role";


    private ApiMessageCode() {
        throw new ConstructorException();
    }

}
