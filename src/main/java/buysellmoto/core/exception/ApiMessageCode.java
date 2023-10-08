package buysellmoto.core.exception;

import buysellmoto.core.exception.ConstructorException;

public final class ApiMessageCode {

    public static final String DEACTIVATED_USER = "Deactivated User";
    public static final String USER_NOT_EXIST = "User not exist";


    private ApiMessageCode() {
        throw new ConstructorException();
    }

}
