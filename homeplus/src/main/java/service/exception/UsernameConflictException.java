package service.exception;

/**
 * @program: homeplus
 * @description:
 * @author: JSY
 * @create: 2019-08-30 13:24
 **/
public class UsernameConflictException extends ServiceException {

    public UsernameConflictException() {
        super();
    }

    public UsernameConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UsernameConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameConflictException(String message) {
        super(message);
    }

    public UsernameConflictException(Throwable cause) {
        super(cause);
    }
}
