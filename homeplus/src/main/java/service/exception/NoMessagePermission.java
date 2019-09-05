package service.exception;

/**
 * @program: homeplus
 * @description:
 * @author: JSY
 * @create: 2019-08-31 23:30
 **/
public class NoMessagePermission extends ServiceException {
    public NoMessagePermission() {
        super();
    }

    public NoMessagePermission(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoMessagePermission(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMessagePermission(String message) {
        super(message);
    }

    public NoMessagePermission(Throwable cause) {
        super(cause);
    }
}
